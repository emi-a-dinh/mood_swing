package com.zybooks.moodswing.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ReservationViewModel : ViewModel() {

    val guestCount = mutableStateOf(1)

    // Add seating location
    val selectedSeating = mutableStateOf<String?>(null)

    // Add sample availability
    val availableSeatingOptions = listOf("Main Dining", "Outdoor", "Bar", "Private Booth")

    val selectedMealPeriod: MutableState<String> =
        mutableStateOf("All Day")

    val lunchTimes: List<String> =
        listOf("11:30 AM", "12:00 PM", "12:30 PM", "1:00 PM")
    val dinnerTimes: List<String> =
        listOf("5:00 PM", "5:30 PM", "6:00 PM", "6:30 PM")
}
