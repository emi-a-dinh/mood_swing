package com.zybooks.moodswing.ui.reservations

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)

data class Reservation(val diningTime: String, val dateTime: LocalDateTime, val location: String, val guestCount : Int) {
    private val dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d", Locale.ENGLISH)
    private val timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)

    val displayDate: String get() = dateTime.format(dateFormatter)
    val displayTime: String get() = dateTime.format(timeFormatter)
    val getDateTime: LocalDateTime get() = dateTime
}