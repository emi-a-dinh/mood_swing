package com.zybooks.moodswing.ui


import androidx.compose.runtime.mutableStateOf

class SignUpViewModel (private val appStorage: AppStorage){
    // Input fields
    val username = mutableStateOf("")
    val password = mutableStateOf("")
    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")


    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val loginSuccess = mutableStateOf(false)

    suspend fun createUser() {
        isLoading.value = true
        try {
            // Validate input
            if (firstName.value.isBlank() || lastName.value.isBlank() ||
                password.value.isBlank() || username.value.isBlank()) {
                errorMessage.value = "All fields are required"
                return
            }

            // Password strength check
            if (password.value.length < 8) {
                errorMessage.value = "Password must be at least 8 characters"
                return
            }

            val userId = generateUserId()
            appStorage.setCurrentUser(userId)
            appStorage.saveFirstName(userId, firstName.value)
            appStorage.saveLastName(userId, lastName.value)
            appStorage.savePassword(userId, password.value)
            appStorage.saveUsername(userId, username.value)
            appStorage.saveRewards(userId, 0)

            loginSuccess.value = true
        } catch (e: Exception) {
            errorMessage.value = "Failed to create user: ${e.message}"
        } finally {
            isLoading.value = false
        }
    }


    private suspend fun generateUserId(): Int {
        var userId: Int
        do {
            userId = (0..999999).random() // Generate a random user ID
        } while (appStorage.getUserIdExists(userId)) // Keep generating until unique
        return userId
    }


}