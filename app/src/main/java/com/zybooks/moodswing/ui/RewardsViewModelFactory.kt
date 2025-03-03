package com.zybooks.moodswing.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RewardsViewModelFactory(private val appStorage: AppStorage) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RewardsViewModel::class.java)) {
            return RewardsViewModel(appStorage) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
