package com.zybooks.moodswing.ui.reservations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zybooks.moodswing.ui.AppStorage

class ReservationViewModelFactory(private val appStorage: AppStorage) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReservationViewModel(appStorage) as T
    }
}
