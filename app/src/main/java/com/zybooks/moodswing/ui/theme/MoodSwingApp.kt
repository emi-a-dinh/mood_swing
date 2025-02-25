package com.zybooks.moodswing.ui.theme

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

sealed class Routes {
    @Serializable
    data object Home

    @Serializable
    data object Messages

    @Serializable
    data object Favorites
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodSwingApp(){
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Title") }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { Unit }) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Stock info",
                        )
                    }
                    IconButton(onClick = { Unit }) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Stock notifications",
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            currentTime = getLatestTime()
                            latestStockPrices = getLatestStockPrices()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh Data"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(latestStockPrices) { price ->
                Text(text = "$$price")
            }
        }
    }
}


}