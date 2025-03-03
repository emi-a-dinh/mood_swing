package com.zybooks.moodswing.ui

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


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

        private object PreferenceKeys {
            val CURRENT_USER_ID = intPreferencesKey("current_user_id")
            val PUSH_NOTIFICATIONS = booleanPreferencesKey("push_notifications")

            fun userFirstNameKey(userId: Int) = stringPreferencesKey("user_${userId}_first_name")
            fun userLastNameKey(userId: Int) = stringPreferencesKey("user_${userId}_last_name")
            fun userRewardsKey(userId: Int) = intPreferencesKey("user_${userId}_rewards")
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
