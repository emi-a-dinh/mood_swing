package com.zybooks.moodswing.ui

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import at.favre.lib.crypto.bcrypt.BCrypt
import com.zybooks.moodswing.ui.reservations.Reservation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

data class AppPreferences(
    val userId: Int = 0,
    val firstName: String = "",
    val lastName: String = "",
    val pushNotifications: Boolean = false,
    val rewards: Int = 0,
    val reservation: Reservation? = null,
)

class AppStorage(private val context: Context) {
    companion object {
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore("app_storage")

        private object PreferenceKeys {
            val CURRENT_USER_ID = intPreferencesKey("current_user_id")

            fun userFirstNameKey(userId: Int) = stringPreferencesKey("user_${userId}_first_name")
            fun userLastNameKey(userId: Int) = stringPreferencesKey("user_${userId}_last_name")
            fun userRewardsKey(userId: Int) = intPreferencesKey("user_${userId}_rewards")
            fun userReservation(userId: Int) = stringPreferencesKey("user_${userId}_reservation")
            fun userPushNotifications(userId: Int) = booleanPreferencesKey("user_${userId}_push_notifications")
            fun userPasswordKey(userId: Int) = stringPreferencesKey("user_${userId}_password")
            fun userUsernameKey(userId: Int) = stringPreferencesKey("user_${userId}_username")
        }
    }

    suspend fun saveReservation(userId: Int, reservation: Reservation) {
        context.datastore.edit { preferences ->
            val reservationData = "${reservation.diningTime}|${reservation.dateTime}|${reservation.location}|${reservation.guestCount}"
            preferences[PreferenceKeys.userReservation(userId)] = reservationData
        }
    }
/*
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
*/
    // Get preferences for a specific user
    fun getUserPreferencesFlow(userId: Int): Flow<AppPreferences> =
        context.datastore.data.map { prefs ->
            val firstName = prefs[PreferenceKeys.userFirstNameKey(userId)] ?: ""
            val lastName = prefs[PreferenceKeys.userLastNameKey(userId)] ?: ""
            val pushNotifications = prefs[PreferenceKeys.userPushNotifications(userId)] ?: false
            val rewards = prefs[PreferenceKeys.userRewardsKey(userId)] ?: 0


            AppPreferences(userId, firstName, lastName, pushNotifications, rewards)
        }

    // Get preferences for the current user
    @RequiresApi(Build.VERSION_CODES.O)
    val appPreferencesFlow: Flow<AppPreferences> =
        context.datastore.data.map { prefs ->
            val userId = prefs[PreferenceKeys.CURRENT_USER_ID] ?: 0
            val firstName = prefs[PreferenceKeys.userFirstNameKey(userId)] ?: ""
            val lastName = prefs[PreferenceKeys.userLastNameKey(userId)] ?: ""
            val pushNotifications = prefs[PreferenceKeys.userPushNotifications(userId)] ?: false
            val rewards = prefs[PreferenceKeys.userRewardsKey(userId)] ?: 0
            val password = prefs[PreferenceKeys.userPasswordKey(userId)] ?: ""
            val reservation = prefs[PreferenceKeys.userReservation(userId)]?.let {
                val parts = it.split("|")
                Reservation(
                    diningTime = parts[0],
                    dateTime = LocalDateTime.parse(parts[1]),
                    location = parts[2],
                    guestCount = parts[3].toInt()
                )
            }
            AppPreferences(userId, firstName, lastName, pushNotifications, rewards, reservation)
        }

    suspend fun setCurrentUser(userId: Int) {
        context.datastore.edit { prefs ->
            prefs[PreferenceKeys.CURRENT_USER_ID] = userId
        }
    }

    suspend fun saveUsername(userId: Int, username: String) {
        context.datastore.edit { prefs ->
            prefs[PreferenceKeys.userUsernameKey(userId)] = username
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

    suspend fun savePushNotifications(userId: Int, enabled: Boolean) {
        context.datastore.edit { prefs ->
            prefs[PreferenceKeys.userPushNotifications(userId)] = enabled
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

    suspend fun deleteReservation(userId: Int) {
        context.datastore.edit { preferences ->
            // Remove the reservation entry for this user
            preferences.remove(PreferenceKeys.userReservation(userId))
        }
    }

    suspend fun savePassword(userId: Int, password: String) {
        // Use Bcrypt to hash the password
        val hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray())

        context.datastore.edit { prefs ->
            prefs[PreferenceKeys.userPasswordKey(userId)] = hashedPassword
        }
    }

    suspend fun findUserId(username: String): Int?{
        val preferences = context.datastore.data.first()
        for (entry in preferences.asMap()) {
            val key = entry.key
            if (key.name.contains("user_") && key.name.contains("_username")) {
                val storedUsername = preferences[key]?.toString()
                if (storedUsername == username) {
                    val parts = key.name.split("_")
                    if (parts.size >= 2) {
                        return parts[1].toIntOrNull()
                    }
                }
            }
        }
        return null
    }

    suspend fun verifyPassword(userId: Int, inputPassword: String): Boolean {

        val preferences = context.datastore.data.first()
        val storedHash = preferences[PreferenceKeys.userPasswordKey(userId)] ?: return false

        // Verify the provided password against the stored hash
        return BCrypt.verifyer().verify(inputPassword.toCharArray(), storedHash).verified
    }

    suspend fun getUserIdExists(userId: Int): Boolean {
        val preferences = context.datastore.data.first()
        return preferences[PreferenceKeys.userFirstNameKey(userId)] != null
    }
}
