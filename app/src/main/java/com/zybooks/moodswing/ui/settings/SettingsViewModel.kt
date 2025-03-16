package com.zybooks.moodswing.ui.settings

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zybooks.moodswing.ui.AppPreferences
import com.zybooks.moodswing.ui.AppStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class SettingsViewModel(private val appStorage: AppStorage) : ViewModel() {
    private val _currentUserPrefs = MutableStateFlow(AppPreferences())
    val currentUserPrefs: StateFlow<AppPreferences> = _currentUserPrefs
    private val currentUserId get() = _currentUserPrefs.value.userId

    val pushNotificationsEnabled: StateFlow<Boolean> =
        _currentUserPrefs.map { it.pushNotifications }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(), false
        )

    init {
        viewModelScope.launch {
            appStorage.appPreferencesFlow.collectLatest { prefs ->
                _currentUserPrefs.value = prefs
            }
        }
    }

    fun setPushNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            appStorage.savePushNotifications(
                currentUserId,
                enabled
            )
        }
    }

    fun updateName(firstName: String, lastName: String) {
        viewModelScope.launch {
            Log.d("ChangeName", "ChangeName User ID: $currentUserId")
            appStorage.updateUserProfile(currentUserId, firstName, lastName)
        }
    }

    fun changePassword(
        currentPassword: String,
        newPassword: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val userId = currentUserId
            Log.d("ChangePassword", "Current User ID: $userId")
            val isValid = appStorage.verifyPassword(userId, currentPassword)
            Log.d("ChangePassword", "Is password valid? $isValid")
            if (isValid) {
                appStorage.savePassword(userId, newPassword)
                Log.d("ChangePassword", "Password changed successfully")
                onResult(true)
            } else {
                Log.d("ChangePassword", "Incorrect current password")
                onResult(false)
            }
        }
    }
}
