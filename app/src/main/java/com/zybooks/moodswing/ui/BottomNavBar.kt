package com.zybooks.moodswing.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomNavBar(navController: NavController) {
    // Step 4.1 - Track current screen
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Step 4.2 - Create navigation bar
    NavigationBar {
        AppScreen.entries.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route.toString(),
                onClick = {
                    // Step 4.3 - Handle navigation clicks
                    navController.navigate(screen.route) {
                        // Clear back stack to prevent multiple screens
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(screen.icon, screen.title) },
                label = { Text(screen.title) }
            )
        }
    }
}