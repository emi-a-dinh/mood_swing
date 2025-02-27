package com.zybooks.moodswing.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ReservationViewModel : ViewModel() {

    val guestCount = mutableStateOf(1)

    // Add seating location
    val selectedSeating = mutableStateOf<String?>(null)

    // Add sample availability
    val availableSeatingOptions = listOf("Main Dining", "Outdoor", "Bar", "Private Booth")


}
