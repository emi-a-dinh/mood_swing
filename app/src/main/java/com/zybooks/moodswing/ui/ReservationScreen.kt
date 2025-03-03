package com.zybooks.moodswing.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservationScreen(viewModel: ReservationViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally){

        Text(
            text = "Hello,",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Let's get you a reservation.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text("How many people?", style = MaterialTheme.typography.headlineSmall)
        GuestCounter(
            guestCount = viewModel.guestCount.collectAsState().value,
            viewModel::alterGuestCount
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth().height(85.dp).padding(start = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item{
                DatePickerComponent(viewModel)}
            item{
                TimePickerComponent(viewModel)}
            item{
                DiningTypeComponent(viewModel)
            }
        }
        AvailableTimesSection(viewModel)
    }
}

@Composable
fun DiningTypeComponent(viewModel: ReservationViewModel){
    var expanded by remember { mutableStateOf(false) }
    val currentDiningTime by viewModel.selectedDiningTime.collectAsState()
    val diningTimes by viewModel.diningTimes.collectAsState()


    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Dining Type", fontSize = 14.sp, fontWeight = FontWeight.Medium, )
        OutlinedButton (onClick={expanded = !expanded}, shape = RoundedCornerShape(4.dp)) {
            Text(text = currentDiningTime)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = {expanded = false}) {
            diningTimes.forEach{
                item ->
                DropdownMenuItem(
                    text = {Text(item)},
                    onClick = {viewModel.selectDiningTime(item); expanded = false}
                )
            }
        }
    }

}


@Composable
fun DatePickerComponent(viewModel: ReservationViewModel) {
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
                viewModel.alterDate(dateFormatter.format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
        showDatePicker.value = false
    }

    Column (modifier = Modifier.fillMaxWidth()){
        Text(text = "Date", fontSize = 14.sp, fontWeight = FontWeight.Medium, )
        OutlinedButton(
            onClick = { showDatePicker.value = true },
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray),
//            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
            modifier = Modifier.width(190.dp)
        ) {
            Text(text = selectedDate.value, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
        }
    }
}

@Composable
fun TimePickerComponent(viewModel: ReservationViewModel) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val timeFormatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }

    val selectedTime = viewModel.time.collectAsState()
    val showTimePicker = remember { mutableStateOf(false) }
    var showAlertDialog = remember { mutableStateOf(false) }

    val minHour = 12  // Earliest selectable hour (12:00 PM)
    val maxHour = 21 // Latest selectable hour (9:00 PM)

    if (showTimePicker.value) {
        TimePickerDialog(
            context,
            { _, hour, minute ->
                if (hour in minHour..maxHour) {
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    viewModel.alterTime(timeFormatter.format(calendar.time))
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

    Column (modifier = Modifier.fillMaxWidth()){
        Text(text = "Time", fontSize = 14.sp, fontWeight = FontWeight.Medium)
        OutlinedButton(
            onClick = { showTimePicker.value = true },
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray),
            modifier = Modifier.width(190.dp)
        ) {
            Text(text = selectedTime.value, fontSize = 16.sp)
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
        }
    }

    // AlertDialog for invalid time selection
    if (showAlertDialog.value) {
        AlertDialog(
            title = { Text(text = "Invalid Time Selection") },
            text = { Text(text = "Please select a time between 12:00 PM to 9:00 PM") },
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
fun GuestCounter(guestCount: Int, alterGuestCount: (Int) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = { if(guestCount > 1) alterGuestCount(-1) }) {
            Text("-")
        }

        Text("$guestCount people", modifier = Modifier.padding(16.dp))

        Button(onClick = { alterGuestCount(1) }) {
            Text("+")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun parseTime(time: String): LocalTime {
    val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)
    return LocalTime.parse(time, formatter)
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AvailableTimesSection(viewModel: ReservationViewModel) {
    val availableReservations by viewModel.availableReservation.collectAsState()
    val selectedDate = viewModel.date.collectAsState()
    val selectedTime = viewModel.time.collectAsState()
    val selectedDiningTime = viewModel.selectedDiningTime.collectAsState()
    val diningTime = viewModel.selectedDiningTime.collectAsState().value == "All Day"

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Available Times",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )


        val lunchReservations = availableReservations.filter { it.diningTime == "Lunch" && it.date == selectedDate.value && parseTime(it.time) >= parseTime(selectedTime.value)}
        val dinnerReservations = availableReservations.filter { it.diningTime == "Dinner" && it.date == selectedDate.value && parseTime(it.time) >= parseTime(selectedTime.value)}


        if (diningTime){
            if (lunchReservations.isNotEmpty()) {
                Text(text = "Lunch", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.padding(vertical = 8.dp)) {
                    items(lunchReservations) { reservation ->
                        TimeCard(timeLabel = reservation.time, seatingLocation = reservation.location)
                    }
                }
            }
            if (dinnerReservations.isNotEmpty()){
                Text(text = "Dinner", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.padding(vertical = 8.dp)) {
                    items(dinnerReservations) { reservation ->
                        TimeCard(timeLabel = reservation.time, seatingLocation = reservation.location)
                    }
                }
            }
        }
        else {
            if (selectedDiningTime.value == "Lunch"){
                Text(text = "Lunch", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.padding(vertical = 8.dp)) {
                    items(lunchReservations) { reservation ->
                        TimeCard(timeLabel = reservation.time, seatingLocation = reservation.location)
                    }
                }
            }
            else{
                Text(text = "Dinner", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.padding(vertical = 8.dp)) {
                    items(dinnerReservations) { reservation ->
                        TimeCard(timeLabel = reservation.time, seatingLocation = reservation.location)
                    }
                }
            }
        }
    }
}

@Composable
fun TimeCard(timeLabel: String, seatingLocation: String) {
    OutlinedButton(
        onClick = { /* Handle reservation selection */ },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray),
        modifier = Modifier.padding(4.dp).fillMaxWidth()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = timeLabel, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            Text(
                text = seatingLocation,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(top=4.dp)
            )
        }
    }
}

