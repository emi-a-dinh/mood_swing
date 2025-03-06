package com.zybooks.moodswing.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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
            appStorage.savePushNotifications(enabled)
        }
    }

    fun updateName(firstName: String, lastName: String) {
        viewModelScope.launch {
            appStorage.updateUserProfile(currentUserId, firstName, lastName)
        }
    }
}
