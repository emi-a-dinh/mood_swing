package com.zybooks.moodswing.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class SettingsViewModel {

    // Profile Section
    val userProfile = mutableStateOf<UserProfile?>(null)
    val profilePictureUrl = mutableStateOf<String?>(null)

    // Account Security
    val currentPassword = mutableStateOf("")
    val newPassword = mutableStateOf("")
    val confirmPassword = mutableStateOf("")

    // Payment Methods
    val paymentMethods = mutableStateListOf<PaymentMethod>()
    val selectedPaymentMethod = mutableStateOf<PaymentMethod?>(null)

    // Preferences
    val notificationsEnabled = mutableStateOf(true)
    val darkModeEnabled = mutableStateOf(false)

    // State Management
    val isUpdatingProfile = mutableStateOf(false)
    val settingsError = mutableStateOf<String?>(null)

    data class PaymentMethod(
        val id: String,
        val cardLast4: String,
        val expiration: String,
        val type: CardType
    ) {
        enum class CardType { VISA, MASTERCARD, AMEX }
    }

    data class UserProfile(
        val name: String,
        val email: String,
        val phone: String
    )
}