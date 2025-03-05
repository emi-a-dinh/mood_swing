package com.zybooks.moodswing.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ReservationConfirmationScreen(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(24.dp)
    ) {
        Text(
            text = "🎉 Reservation Confirmed!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        Text(
            text = "Thank you for choosing MoodSwing Restaurant",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )

        Button(
            onClick = {
                navController.popBackStack(Routes.Home, inclusive = false)
            },
            modifier = Modifier.padding(24.dp)
        ) {
            Text("Return to Home")
        }
    }
}
