package com.zybooks.moodswing.ui

import androidx.compose.runtime.mutableStateOf

class LoginViewModel {
    // Input fields
    val username = mutableStateOf("")
    val password = mutableStateOf("")

    // State management
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val loginSuccess = mutableStateOf(false)


}