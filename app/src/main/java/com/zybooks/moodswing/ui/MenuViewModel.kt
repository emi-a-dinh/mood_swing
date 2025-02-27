package com.zybooks.moodswing.ui

import androidx.compose.runtime.mutableStateOf

class MenuViewModel {
    // Menu content
    // val menuCategories = mutableStateListOf<MenuCategory>()
    // val selectedCategory = mutableStateOf<MenuCategory?>(null)

    // Search/filter
    val searchQuery = mutableStateOf("")
    val isLoading = mutableStateOf(true)
}