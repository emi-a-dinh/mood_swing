package com.zybooks.moodswing.ui.reservations

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.zybooks.moodswing.ui.AppPreferences
import com.zybooks.moodswing.ui.AppStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
class ReservationViewModel(
    private val appStorage: AppStorage,
    private val application: Application // Add application context
) : ViewModel() {

    private val _currentUserPrefs = MutableStateFlow(AppPreferences())
    val currentUserPrefs: StateFlow<AppPreferences> = _currentUserPrefs
    private val currentUserId get() = _currentUserPrefs.value.userId

    init {
        viewModelScope.launch {
            appStorage.appPreferencesFlow.collectLatest { prefs ->
                _currentUserPrefs.value = prefs
            }
        }
    }

    fun cancelReservation() {
        viewModelScope.launch {
            appStorage.deleteReservation(currentUserId)
        }
    }

    // In ReservationViewModel
    fun scheduleReminder(reservationTime: LocalDateTime) {
        viewModelScope.launch {
            val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                .setInitialDelay(
                    calculateReminderDelay(reservationTime),
                    TimeUnit.SECONDS
                )
                .setInputData(
                    workDataOf(
                        ReminderWorker.KEY_RESERVATION_TIME to
                                reservationTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    )
                )
                .build()

            WorkManager.getInstance(application).enqueue(workRequest)
        }
    }

    private fun calculateReminderDelay(reservationTime: LocalDateTime): Long {
        val reminderTime = reservationTime.minusMinutes(30)
        return reminderTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() -
                System.currentTimeMillis()
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

    fun saveSelectedReservation() {
        val reservation = _selectedReservation.value ?: return // safety check
        viewModelScope.launch {
            Log.d("Reservation", "Reservation User ID: $currentUserId")
            appStorage.saveReservation(currentUserId, reservation)
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

    private val _selectedReservation = MutableStateFlow<Reservation?>(null)
    val selectedReservation: StateFlow<Reservation?> = _selectedReservation.asStateFlow()

    fun selectReservation(reservation: Reservation) {
        _selectedReservation.value = reservation
    }

    fun alterDate(newDate : LocalDate){
        _selectedDate.value = newDate
    }
}
