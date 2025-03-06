package com.zybooks.moodswing

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zybooks.moodswing.ui.AppStorage
import com.zybooks.moodswing.ui.LoginScreen
import com.zybooks.moodswing.ui.LoginViewModel
import com.zybooks.moodswing.ui.MoodSwingApp
import com.zybooks.moodswing.ui.SignUpFields
import com.zybooks.moodswing.ui.SignUpScreen
import com.zybooks.moodswing.ui.SignUpViewModel
import com.zybooks.moodswing.ui.theme.MoodSwingTheme
import kotlinx.serialization.Serializable



class MainActivity : ComponentActivity() {


    private val permissionRequestLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            val message = if (isGranted) "Permission granted" else "Permission NOT granted"
            Log.i("MainActivity", message)
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) {
                permissionRequestLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        setContent {
            MoodSwingTheme (dynamicColor = false){
                val navController = rememberNavController()
                val appStorage = remember { AppStorage(applicationContext) }


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") { LoginScreen(viewModel = LoginViewModel(appStorage), nav = navController) }
                        composable("signup") { SignUpScreen(viewModel = SignUpViewModel(appStorage), nav = navController) }
                        composable("home"){ MoodSwingApp(appStorage)}
                    }
                    //MoodSwingApp()
                    //LoginScreen()
                }
            }
        }
    }
}

