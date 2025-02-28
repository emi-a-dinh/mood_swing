package com.zybooks.moodswing.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun ReservationScreen(viewModel: ReservationViewModel) {
    Column {
        Text("How many people?", style = MaterialTheme.typography.headlineSmall)
        GuestCounter(
            guestCount = viewModel.guestCount.value,
            onCountChanged = { newCount -> viewModel.guestCount.value = newCount }
        )
        DatePickerComponent()
        TimePickerComponent()
    }

}

@Composable
fun DatePickerComponent() {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormatter = remember { SimpleDateFormat("EEE, MMM d'th'", Locale.getDefault()) }

    val selectedDate = remember { mutableStateOf(dateFormatter.format(calendar.time)) }
    val showDatePicker = remember { mutableStateOf(false) }

    if (showDatePicker.value) {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                calendar.set(year, month, day)
                selectedDate.value = dateFormatter.format(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
        showDatePicker.value = false
    }

    Column {
        Text(text = "Date", fontSize = 14.sp, fontWeight = FontWeight.Medium, )
        OutlinedButton(
            onClick = { showDatePicker.value = true },
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = selectedDate.value, fontSize = 16.sp)
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
        }
    }
}
@Composable
fun TimePickerComponent() {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val timeFormatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

    val selectedTime = remember { mutableStateOf(timeFormatter.format(calendar.time)) }
    val showTimePicker = remember { mutableStateOf(false) }
    var showAlertDialog = remember { mutableStateOf(false) }

    val minHour = 9  // Earliest selectable hour (9:00 AM)
    val maxHour = 17 // Latest selectable hour (5:00 PM)

    if (showTimePicker.value) {
        TimePickerDialog(
            context,
            { _, hour, minute ->
                if (hour in minHour..maxHour) {
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    selectedTime.value = timeFormatter.format(calendar.time)
                } else {
                    showAlertDialog.value = true // Show AlertDialog for invalid time
                }
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false // Set to true for 24-hour format
        ).show()
        showTimePicker.value = false
    }

    Column {
        Text(text = "Time", fontSize = 14.sp, fontWeight = FontWeight.Medium)
        OutlinedButton(
            onClick = { showTimePicker.value = true },
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = selectedTime.value, fontSize = 16.sp)
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
        }
    }

    // AlertDialog for invalid time selection
    if (showAlertDialog.value) {
        AlertDialog(
            title = { Text(text = "Invalid Time Selection") },
            text = { Text(text = "Please select a time between 9:00 AM and 5:00 PM.") },
            onDismissRequest = { showAlertDialog.value = false },
            confirmButton = {
                Button(onClick = { showAlertDialog.value = false }) {
                    Text(text = "Okay")
                }
            }
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
