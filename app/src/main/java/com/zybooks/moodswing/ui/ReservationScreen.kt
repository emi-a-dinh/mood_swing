package com.zybooks.moodswing.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ReservationScreen(viewModel: ReservationViewModel) {
    Column {
        Text("How many people?", style = MaterialTheme.typography.headlineSmall)
        GuestCounter(
            guestCount = viewModel.guestCount.value,
            onCountChanged = { newCount -> viewModel.guestCount.value = newCount }
        )
    }

}

@Composable
fun GuestCounter(guestCount: Int, onCountChanged: (Int) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = { if(guestCount > 1) onCountChanged(guestCount - 1) }) {
            Text("-")
        }

        Text("$guestCount people", modifier = Modifier.padding(16.dp))

        Button(onClick = { onCountChanged(guestCount + 1) }) {
            Text("+")
        }
    }
}
