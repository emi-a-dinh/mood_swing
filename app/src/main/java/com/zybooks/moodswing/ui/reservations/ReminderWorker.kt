package com.zybooks.moodswing.ui.reservations

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.zybooks.moodswing.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReminderWorker(context: Context, parameters: WorkerParameters) : CoroutineWorker(context, parameters) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val CHANNEL_ID = "reservation_reminders"
        const val NOTIFICATION_ID = 100
        const val KEY_RESERVATION_TIME = "reservation_time"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        return try {
            // Parse reservation time from input data
            val reservationTime = LocalDateTime.parse(
                inputData.getString(KEY_RESERVATION_TIME),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
            )

            createNotificationChannel()
            showReminderNotification(reservationTime)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                CHANNEL_ID,
                "Reservation Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Upcoming reservation notifications"
                notificationManager.createNotificationChannel(this)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showReminderNotification(reservationTime: LocalDateTime) {
        val formattedTime = reservationTime.format(
            DateTimeFormatter.ofPattern("h:mm a")
        )

        NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.default_pfp)
            .setContentTitle("Reservation Reminder")
            .setContentText("Your reservation starts at $formattedTime")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build().also { notification ->
                notificationManager.notify(NOTIFICATION_ID, notification)
            }
    }
}


