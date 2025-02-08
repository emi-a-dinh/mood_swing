package com.zybooks.moodswing.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class RewardsViewModel {

    // User points
    val currentSteaks = mutableStateOf(0)

    // Available rewards
    // val availableRewards = mutableStateListOf<Reward>()

    // Redemption status
    val isRedeeming = mutableStateOf(false)

}