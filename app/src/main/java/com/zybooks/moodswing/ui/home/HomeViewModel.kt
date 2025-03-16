package com.zybooks.moodswing.ui.home

import android.app.Notification
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class HomeViewModel {
    // User data
    // val userProfile = mutableStateOf<UserProfile?>(null)

    // Notifications
    val notifications = mutableStateListOf<Notification>()

    // Loading states
    val isRefreshing = mutableStateOf(false)
}