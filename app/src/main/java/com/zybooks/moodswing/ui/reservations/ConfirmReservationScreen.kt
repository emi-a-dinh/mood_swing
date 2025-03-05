package com.zybooks.moodswing.ui.reservations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
    val userPrefs by viewModel.currentUserPrefs.collectAsState()
    val currentReservation = userPrefs.reservation

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        currentReservation?.let {
            // Display reservation details
            ReservationDetailCard(it)
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

