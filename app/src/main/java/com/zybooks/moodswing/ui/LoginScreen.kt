package com.zybooks.moodswing.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zybooks.moodswing.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(viewModel: LoginViewModel, nav : NavController){
    val coroutineScope = rememberCoroutineScope()
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2E0000),),
    ){
        Spacer(modifier = Modifier.padding(32.dp))
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null)
        Spacer(modifier = Modifier.padding(32.dp))
        Text("Login", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
        LoginFields(viewModel, nav, coroutineScope)
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "Don't have an account?", color = Color.White)
        TextButton(
            onClick = {nav.navigate("signup")},
            colors = ButtonDefaults.buttonColors(contentColor = Color.White)
        ){
            Text("Sign up")
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginFields(viewModel: LoginViewModel, nav: NavController, coroutineScope: CoroutineScope){
    var username by viewModel.username
    var password by viewModel.password

    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage
    val loginSuccess by viewModel.loginSuccess


    if (loginSuccess) {
        LaunchedEffect(key1 = Unit) {
            nav.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.width(300.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,

            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            modifier = Modifier.width(300.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                focusedLabelColor = MaterialTheme.colorScheme.secondary,

            )
        )

        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.secondary,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = { coroutineScope.launch { viewModel.login() }},
            enabled = !isLoading,
            modifier = Modifier.width(150.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
        ) {
            if (isLoading){
                Text("Loading...", color = MaterialTheme.colorScheme.secondary)
            }
            else{
            Text("Login", color = Color.Black)}

        }





    }

}





