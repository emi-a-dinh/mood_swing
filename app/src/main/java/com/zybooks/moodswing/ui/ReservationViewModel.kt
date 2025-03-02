package com.zybooks.moodswing.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.LocalTime

class ReservationViewModel : ViewModel() {
    data class Reservation(val diningTime: String, val time: String, val date: String, val location: String, val guestCount : Int)

    private val _availableReservation = MutableStateFlow(listOf(
        Reservation("Lunch", "12:00 PM", "Sun, Mar 2th", "Main Dining", 2),
        Reservation("Lunch", "1:00 PM", "Sun, Mar 2th", "Outdoor", 2),
        Reservation("Lunch", "1:30 PM", "Sun, Mar 2th", "Bar", 2),
        Reservation("Lunch", "2:00 PM", "Sun, Mar 2th", "Private Booth", 2),
        Reservation("Lunch", "2:00 PM", "Sun, Mar 2th", "Main Dining", 2),
        Reservation("Dinner", "5:30 PM", "Sun, Mar 2th", "Main Dining", 2),
        Reservation("Dinner", "6:00 PM", "Sun, Mar 2th", "Outdoor", 2),
        Reservation("Dinner", "6:30 PM", "Sun, Mar 2th", "Bar", 2),
        Reservation("Dinner", "7:00 PM", "Sun, Mar 2th", "Private Booth", 2),
        Reservation("Dinner", "7:00 PM", "Sun, Mar 2th", "Main Dining", 2)
    ))


    val availableReservation : StateFlow<List<Reservation>> = _availableReservation.asStateFlow()

    private val _diningTimes = MutableStateFlow(listOf("All Day", "Lunch", "Dinner"))
    val diningTimes : StateFlow<List<String>> = _diningTimes.asStateFlow()

    private val _selectedDiningTime = MutableStateFlow("All Day")
    val selectedDiningTime: StateFlow<String> = _selectedDiningTime.asStateFlow()

    fun selectDiningTime(category: String) {
        _selectedDiningTime.value = category
    }

    private val _guestCount = MutableStateFlow(1)
    val guestCount : StateFlow<Int> = _guestCount.asStateFlow()

    fun alterGuestCount(value : Int){
        _guestCount.value += value
    }


    private val _time = MutableStateFlow("12:00 PM")

    val time : StateFlow<String> = _time.asStateFlow()


    fun alterTime(value: String){
        _time.value = value
    }

    private val _date = MutableStateFlow("Sun, Mar 2th")
    val date : StateFlow<String> = _date.asStateFlow()

    fun alterDate(value : String){
        _date.value = value
    }


    // Add seating location
    val selectedSeating = mutableStateOf<String?>(null)

    // Add sample availability
    val availableSeatingOptions = listOf("Main Dining", "Outdoor", "Bar", "Private Booth")

}
