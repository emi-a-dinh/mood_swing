package com.zybooks.moodswing.ui.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.zybooks.moodswing.ui.AppStorage

class LoginViewModel(private val appStorage: AppStorage){

    val username = mutableStateOf("")
    val password = mutableStateOf("")


    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val loginSuccess = mutableStateOf(false)

    suspend fun login(){
        isLoading.value = true
        try {
            if (username.value.isBlank() || password.value.isBlank()){
                errorMessage.value = "All fields are required"
                return
            }
            val userId = appStorage.findUserId(username.value)
            if (userId == null){
                errorMessage.value = "User not found"
                return
            }
            Log.d("LoginUserID","UserId: $userId")

            val password_valid = appStorage.verifyPassword(userId, password.value)
            if (!password_valid){
                errorMessage.value = "Incorrect Password"
                return
            }

            loginSuccess.value = true
        } catch (e: Exception) {
            errorMessage.value = "Failed to Login: ${e.message}"
        } finally {
            isLoading.value = false
        }
    }

}