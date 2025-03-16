package com.zybooks.moodswing.ui

import EditProfileScreen
import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zybooks.moodswing.ui.home.HomeScreen
import com.zybooks.moodswing.ui.home.HomeViewModel
import com.zybooks.moodswing.ui.menu.MenuScreen
import com.zybooks.moodswing.ui.menu.MenuViewModel
import com.zybooks.moodswing.ui.reservations.ConfirmReservationScreen
import com.zybooks.moodswing.ui.reservations.ReservationConfirmationScreen
import com.zybooks.moodswing.ui.reservations.ReservationScreen
import com.zybooks.moodswing.ui.reservations.ReservationViewModel
import com.zybooks.moodswing.ui.rewards.RewardsScreen
import com.zybooks.moodswing.ui.rewards.RewardsViewModel
import com.zybooks.moodswing.ui.settings.AboutUsScreen
import com.zybooks.moodswing.ui.settings.ChangePasswordScreen
import com.zybooks.moodswing.ui.settings.PrivacyScreen
import com.zybooks.moodswing.ui.settings.SettingsScreen
import com.zybooks.moodswing.ui.settings.SettingsViewModel
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object Home

    @Serializable
    data object Menu

    @Serializable
    data object Reservation

    @Serializable
    data object Rewards

    @Serializable
    data object Settings
}

enum class AppScreen(
    val route: Any,
    val title: String,
    val icon: ImageVector
) {
    HOME(Routes.Home, "Home", Icons.Default.Home),
    MENU(Routes.Menu, "Menu", Icons.Default.Menu),
    RESERVATION(Routes.Reservation, "Reserve", Icons.Default.Place),
    REWARDS(Routes.Rewards, "Rewards", Icons.Default.Star),
    SETTINGS(Routes.Settings, "Settings", Icons.Default.Settings),
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun MoodSwingApp(appStorage: AppStorage) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val reservationViewModel = remember {
        ReservationViewModel(
            appStorage = appStorage,
            application = context.applicationContext as Application
        )
    }
    val settingsViewModel =
        SettingsViewModel(
            appStorage = appStorage,
    )
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("MoodSwing Restaurant") })
        },
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Home,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Routes.Home> { HomeScreen(viewModel = HomeViewModel()) }
            composable<Routes.Menu> { MenuScreen(viewModel = MenuViewModel()) }
            composable<Routes.Rewards> {
                val viewModel = RewardsViewModel(
                    appStorage = appStorage
                )
                RewardsScreen(viewModel = viewModel)
            }
            composable<Routes.Settings> {
                SettingsScreen(
                    viewModel = settingsViewModel,
                    navController = navController
                )
            }
            composable("edit_profile") {
                EditProfileScreen(
                    viewModel = settingsViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }
            // In MoodSwingApp.kt
            composable<Routes.Reservation> {
                ReservationScreen(reservationViewModel, navController)
            }
            composable("confirm_reservation") {
                ConfirmReservationScreen(reservationViewModel, navController)
            }
            composable("reservation_confirmed") {
                ReservationConfirmationScreen(navController)
            }
            composable("change_password") {
                ChangePasswordScreen(settingsViewModel, onBackClick={navController.popBackStack()})
            }
            composable("about_us") {
                AboutUsScreen(onBackClick={navController.popBackStack()})
            }
            composable("privacy_policy") {
                PrivacyScreen(onBackClick={navController.popBackStack()})
            }
        }
    }
}
