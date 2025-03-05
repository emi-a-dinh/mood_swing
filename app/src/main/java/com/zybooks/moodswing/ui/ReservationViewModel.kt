package com.zybooks.moodswing.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class ReservationViewModel(private val appStorage: AppStorage) : ViewModel() {

    val currentReservation = appStorage.currentReservationFlow

    data class Reservation(val diningTime: String, val dateTime: LocalDateTime, val location: String, val guestCount : Int) {
        private val dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d", Locale.ENGLISH)
        private val timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)

        val displayDate: String get() = dateTime.format(dateFormatter)
        val displayTime: String get() = dateTime.format(timeFormatter)
    }

    fun scheduleReminder(reservationTime: LocalDateTime) {
        viewModelScope.launch {
            val reminderTime = reservationTime
                .minusMinutes(30)
                .atZone(ZoneId.systemDefault())
                .toInstant().
                toEpochMilli()

            // Calculate 30 minutes before reservation
            val delayMillis = reminderTime - System.currentTimeMillis()

            if (delayMillis > 0) {
                delay(delayMillis)
                appStorage.showReservationReminder()
            }
        }
    }
    private val baseDate = LocalDateTime.now()

    private val _availableReservation = MutableStateFlow(listOf(
        // Lunch times (12 PM - 2 PM)
        Reservation("Lunch", baseDate.withHour(12).withMinute(0), "Main Dining", 2),
        Reservation("Lunch", baseDate.withHour(13).withMinute(0), "Outdoor", 2), // 1 PM
        Reservation("Lunch", baseDate.withHour(13).withMinute(30), "Bar", 2),    // 1:30 PM
        Reservation("Lunch", baseDate.withHour(14).withMinute(0), "Private Booth", 2), // 2 PM

        // Dinner times (5:30 PM - 7 PM)
        Reservation("Dinner", baseDate.withHour(17).withMinute(30), "Main Dining", 2), // 5:30 PM
        Reservation("Dinner", baseDate.withHour(18).withMinute(0), "Outdoor", 2),      // 6 PM
        Reservation("Dinner", baseDate.withHour(18).withMinute(30), "Bar", 2),         // 6:30 PM
        Reservation("Dinner", baseDate.withHour(19).withMinute(0), "Private Booth", 2) // 7 PM
    ))

    fun saveReservation(reservation: Reservation) {
        viewModelScope.launch {
            // Get current user ID from storage
            val userId = appStorage.appPreferencesFlow.first().userId
            appStorage.saveReservation(userId, reservation)
            scheduleReminder(reservation.dateTime)
        }
    }

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

    private val _selectedTime = MutableStateFlow(LocalTime.now())

    val selectedTime : StateFlow<LocalTime> = _selectedTime.asStateFlow()

    fun alterTime(newTime: LocalTime){
        _selectedTime.value = newTime
    }

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate : StateFlow<LocalDate> = _selectedDate.asStateFlow()

    fun alterDate(newDate : LocalDate){
        _selectedDate.value = newDate
    }

    private val _selectedReservation = MutableStateFlow<Reservation?>(null)
    val selectedReservation: StateFlow<Reservation?> = _selectedReservation.asStateFlow()

    fun setSelectedReservation(reservation: Reservation) {
        _selectedReservation.value = reservation
    }
    // Add seating location
    val selectedSeating = mutableStateOf<String?>(null)

    // Add sample availability
    val availableSeatingOptions = listOf("Main Dining", "Outdoor", "Bar", "Private Booth")

}
