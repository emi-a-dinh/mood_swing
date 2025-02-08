package com.zybooks.moodswing.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import java.time.LocalDate
import java.time.LocalTime

class ReservationViewModel {


    // Time slots by meal type
    // val lunchSlots = mutableStateListOf<TimeSlot>()
    // val dinnerSlots = mutableStateListOf<TimeSlot>()

    // Reservation details
    val selectedDate = mutableStateOf<LocalDate?>(null)
    val selectedTime = mutableStateOf<LocalTime?>(null)
    val guestSize = mutableStateOf(1)

    // Submission status
    val isSubmitting = mutableStateOf(false)
}