package com.zybooks.moodswing.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RewardsViewModel(private val appStorage: AppStorage) : ViewModel() {
    // Current user ID - in a real app, you'd get this from a user session
    private val currentUserId = 1

    // Track rewards as a StateFlow
    private val _rewardsCount = MutableStateFlow(0)
    val rewardsCount: StateFlow<Int> = _rewardsCount

    // Progress as a StateFlow (0.0f to 1.0f)
    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress

    // Initialize by loading from DataStore
    init {
        viewModelScope.launch {
            appStorage.getUserPreferencesFlow(currentUserId).collectLatest { preferences ->
                _rewardsCount.value = preferences.rewards
                _progress.value = preferences.rewards / 10f
            }
        }
    }

    // Function to add a reward
    fun addReward() {
        viewModelScope.launch {
            appStorage.incrementRewards(currentUserId)
        }
    }

    // Function to reset rewards
    fun resetRewards() {
        viewModelScope.launch {
            appStorage.saveRewards(currentUserId, 0)
        }
    }
}
