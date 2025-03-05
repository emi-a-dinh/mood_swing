package com.zybooks.moodswing.ui

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zybooks.moodswing.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime


data class AppPreferences (
    val userId: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val pushNotifications: Boolean = false,
    val rewards: Int = 0
)

class AppStorage(private val context: Context) {
    companion object {
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore("app_storage")

        private const val CHANNEL_ID_RESERVATION = "reservation_reminders"
        private const val NOTIFICATION_ID = 100

        private fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID_RESERVATION,
                    "Reservation Reminders",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Reminders for upcoming reservations"
                }

                context.getSystemService(NotificationManager::class.java)
                    ?.createNotificationChannel(channel)
            }
        }

        private object PreferenceKeys {
            val CURRENT_USER_ID = intPreferencesKey("current_user_id")
            val PUSH_NOTIFICATIONS = booleanPreferencesKey("push_notifications")
            val CURRENT_RESERVATION = stringPreferencesKey("current_reservation")

            fun userFirstNameKey(userId: Int) = stringPreferencesKey("user_${userId}_first_name")
            fun userLastNameKey(userId: Int) = stringPreferencesKey("user_${userId}_last_name")
            fun userRewardsKey(userId: Int) = intPreferencesKey("user_${userId}_rewards")
        }
    }

    suspend fun saveReservation(userId: Int, reservation: ReservationViewModel.Reservation) {
        context.datastore.edit { preferences ->
            val reservationData = "${reservation.diningTime}|${reservation.dateTime}|${reservation.location}|${reservation.guestCount}"
            preferences[PreferenceKeys.CURRENT_RESERVATION] = reservationData
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val currentReservationFlow: Flow<ReservationViewModel.Reservation?> =
        context.datastore.data.map { preferences ->
            preferences[PreferenceKeys.CURRENT_RESERVATION]?.let {
                val parts = it.split("|")
                ReservationViewModel.Reservation(
                    diningTime = parts[0],
                    dateTime = LocalDateTime.parse(parts[1]),
                    location = parts[2],
                    guestCount = parts[3].toInt()
                )
            }
        }

    @SuppressLint("ServiceCast")
    suspend fun showReservationReminder() {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Check if notifications are enabled in settings
        val prefs = context.datastore.data.first()
        val notificationsEnabled = prefs[PreferenceKeys.PUSH_NOTIFICATIONS] ?: false

        if (notificationsEnabled) {
            val notification = NotificationCompat.Builder(context, CHANNEL_ID_RESERVATION)
                .setSmallIcon(R.drawable.default_pfp)
                .setContentTitle("Check-In Reminder")
                .setContentText("Check in within 30 minutes to keep your reservation!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    // Get preferences for a specific user
    fun getUserPreferencesFlow(userId: Int): Flow<AppPreferences> =
        context.datastore.data.map { prefs ->
            val firstName = prefs[PreferenceKeys.userFirstNameKey(userId)] ?: ""
            val lastName = prefs[PreferenceKeys.userLastNameKey(userId)] ?: ""
            val pushNotifications = prefs[PreferenceKeys.PUSH_NOTIFICATIONS] ?: false
            val rewards = prefs[PreferenceKeys.userRewardsKey(userId)] ?: 0

            AppPreferences(userId, firstName, lastName, pushNotifications, rewards)
        }

    // Get preferences for the current user
    val appPreferencesFlow: Flow<AppPreferences> =
        context.datastore.data.map { prefs ->
            val userId = prefs[PreferenceKeys.CURRENT_USER_ID] ?: 0
            val firstName = prefs[PreferenceKeys.userFirstNameKey(userId)] ?: ""
            val lastName = prefs[PreferenceKeys.userLastNameKey(userId)] ?: ""
            val pushNotifications = prefs[PreferenceKeys.PUSH_NOTIFICATIONS] ?: false
            val rewards = prefs[PreferenceKeys.userRewardsKey(userId)] ?: 0

            AppPreferences(userId, firstName, lastName, pushNotifications, rewards)
        }

    suspend fun setCurrentUser(userId: Int) {
        context.datastore.edit { prefs ->
            prefs[PreferenceKeys.CURRENT_USER_ID] = userId
        }
    }

    suspend fun saveFirstName(userId: Int, firstName: String) {
        context.datastore.edit { prefs ->
            prefs[PreferenceKeys.userFirstNameKey(userId)] = firstName
        }
    }

    suspend fun saveLastName(userId: Int, lastName: String) {
        context.datastore.edit { prefs ->
            prefs[PreferenceKeys.userLastNameKey(userId)] = lastName
        }
    }

    suspend fun savePushNotifications(enabled: Boolean) {
        context.datastore.edit { prefs ->
            prefs[PreferenceKeys.PUSH_NOTIFICATIONS] = enabled
        }
    }

    suspend fun saveRewards(userId: Int, rewards: Int) {
        context.datastore.edit { prefs ->
            prefs[PreferenceKeys.userRewardsKey(userId)] = rewards.coerceIn(0, 10)  // Ensure rewards are between 0-10
        }
    }

    // Increment rewards by 1 for a specific user
    suspend fun incrementRewards(userId: Int) {
        context.datastore.edit { prefs ->
            val currentRewards = prefs[PreferenceKeys.userRewardsKey(userId)] ?: 0
            if (currentRewards < 10) {  // Max of 10 rewards
                prefs[PreferenceKeys.userRewardsKey(userId)] = currentRewards + 1
            }
        }
    }

    // Convenience method to update user profile information at once
    suspend fun updateUserProfile(userId: Int, firstName: String, lastName: String) {
        context.datastore.edit { prefs ->
            prefs[PreferenceKeys.userFirstNameKey(userId)] = firstName
            prefs[PreferenceKeys.userLastNameKey(userId)] = lastName
        }
    }
}
