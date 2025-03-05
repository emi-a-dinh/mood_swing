package com.zybooks.moodswing.ui.reservations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Reusable across ConfirmReservationScreen and ReservationScreen
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservationDetailCard(reservation: Reservation) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Your Reservation", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.padding(16.dp))
        ReservationDetailItem("Date", reservation.displayDate)
        ReservationDetailItem("Time", reservation.displayTime)
        ReservationDetailItem("Location", reservation.location)
        ReservationDetailItem("Party Size", "${reservation.guestCount} people")
    }
}
