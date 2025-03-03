package com.zybooks.moodswing.ui

import EditProfileScreen
import android.annotation.SuppressLint
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
fun MoodSwingApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val appStorage = remember { AppStorage(context) }

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
            composable<Routes.Reservation> { ReservationScreen(viewModel = ReservationViewModel()) }
            composable<Routes.Rewards> {
                val viewModel = viewModel<RewardsViewModel>(
                    factory = RewardsViewModelFactory(appStorage)
                )
                RewardsScreen(viewModel = viewModel)
            }
            composable<Routes.Settings> {
                val viewModel = viewModel<SettingsViewModel>(
                    factory = SettingsViewModelFactory(appStorage)
                )
                SettingsScreen(
                    viewModel = viewModel,
                    onEditProfileClick = { navController.navigate("edit_profile") }
                )
            }

            composable("edit_profile") {
                val viewModel = viewModel<SettingsViewModel>(
                    factory = SettingsViewModelFactory(appStorage)
                )
                EditProfileScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }

        }
    }
}
