package com.zybooks.moodswing.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel : ViewModel() {
    private val _pushNotificationsEnabled = MutableStateFlow(false)
    val pushNotificationsEnabled: StateFlow<Boolean> = _pushNotificationsEnabled

    private val _firstName = MutableStateFlow("Default")
    val firstName: StateFlow<String> = _firstName

    private val _lastName = MutableStateFlow("Name")
    val lastName: StateFlow<String> = _lastName

    fun setPushNotificationsEnabled(enabled: Boolean) {
        _pushNotificationsEnabled.value = enabled
    }

    fun updateName(firstName: String, lastName: String) {
        _firstName.value = firstName
        _lastName.value = lastName
    }
}

