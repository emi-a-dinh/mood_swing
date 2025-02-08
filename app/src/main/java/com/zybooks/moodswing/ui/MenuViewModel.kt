package com.zybooks.moodswing.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class MenuViewModel {

    // Menu content
    // val menuCategories = mutableStateListOf<MenuCategory>()
    // val selectedCategory = mutableStateOf<MenuCategory?>(null)

    // Search/filter
    val searchQuery = mutableStateOf("")

    // Loading state
    val isLoading = mutableStateOf(true)

}