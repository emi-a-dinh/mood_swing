package com.zybooks.moodswing.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RewardsViewModel(private val appStorage: AppStorage) : ViewModel() {

    // Track current user ID and rewards
    private var currentUserId = 0
    private val _rewardsCount = MutableStateFlow(0)
    val rewardsCount: StateFlow<Int> = _rewardsCount

    // Progress state (0.0f to 1.0f)
    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress

    init {
        // Observe current user changes and their rewards
        viewModelScope.launch {
            appStorage.appPreferencesFlow.collectLatest { preferences ->
                currentUserId = preferences.userId
                _rewardsCount.value = preferences.rewards
                _progress.value = preferences.rewards / 10f
            }
        }
    }

    fun addReward() {
        viewModelScope.launch {
            appStorage.incrementRewards(currentUserId)
        }
    }

    fun resetRewards() {
        viewModelScope.launch {
            appStorage.saveRewards(currentUserId, 0)
        }
    }
}
