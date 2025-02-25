package com.zybooks.moodswing.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable


sealed class Routes {
    @Serializable
    data object Home

    @Serializable
    data object Messages

    @Serializable
    data object Favorites
}

enum class AppScreen(val route: Any, val title: String, val icon: ImageVector) {
    HOME(Routes.Home, "Home", Icons.Default.Home),
    MESSAGES(Routes.Messages, "Messages", Icons.Default.Email),
    FAVORITES(Routes.Favorites, "Favorites", Icons.Default.Favorite)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodSwingApp(modifier: Modifier = Modifier){

    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Menu") }
            )
        },
        bottomBar = {
            BottomAppBar (modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { /* Handle info click */ }) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Home"
                    )
                }
                IconButton(onClick = { /* Handle notifications click */ }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu"
                    )
                }
                IconButton(onClick = { /* Handle notifications click */ }) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Rewards"
                    )
                }
                IconButton(onClick = { /* Handle notifications click */ }) {
                    Icon(
                        imageVector = Icons.Filled.Place,
                        contentDescription = "Reserve"
                    )
                }
                IconButton(onClick = { /* Handle notifications click */ }) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings"
                    )
                }

            }
        }
    ) {
        // Content goes here, no inner padding handling needed
    }
}

