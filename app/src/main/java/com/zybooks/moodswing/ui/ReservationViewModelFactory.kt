package com.zybooks.moodswing.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ReservationViewModelFactory(private val appStorage: AppStorage) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReservationViewModel(appStorage) as T
    }
}
