package com.zybooks.moodswing.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ConfirmReservationScreen(
    viewModel: ReservationViewModel,
    navController: NavController
) {
    val reservation by viewModel.selectedReservation.collectAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        reservation?.let {
            // Display reservation details
            Text("Confirm Reservation", style = MaterialTheme.typography.headlineMedium)
            ReservationDetailItem("Date", it.displayDate)
            ReservationDetailItem("Time", it.displayTime)
            ReservationDetailItem("Location", it.location)
            ReservationDetailItem("Party Size", "${it.guestCount} people")

            Button(
                onClick = {
                    viewModel.saveReservation(it)
                    navController.navigate("reservation_confirmed")
                }
            ) {
                Text("Confirm Reservation")
            }
        } ?: Text("No reservation selected", color = Color.Gray)
    }
}
@Composable
fun ReservationDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(120.dp)
        )
        Text(value)
    }
}

