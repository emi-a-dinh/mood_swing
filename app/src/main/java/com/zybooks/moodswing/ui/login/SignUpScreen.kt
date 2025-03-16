package com.zybooks.moodswing.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
fun SignUpScreen(viewModel: SignUpViewModel, nav : NavController){
    val coroutineScope = rememberCoroutineScope()

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(Color(0xFF2E0000)),
    ){
        Spacer(modifier = Modifier.padding(32.dp))
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null)
        Spacer(modifier = Modifier.padding(32.dp))
        Text("Sign Up", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
        SignUpFields(viewModel, coroutineScope, nav)
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = "Have an Account?", color = Color.White)
        TextButton(
            onClick = {nav.navigate("login")},
            colors = ButtonDefaults.buttonColors(contentColor = Color.White)
        ){
            Text("Login")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpFields(viewModel: SignUpViewModel, coroutineScope: CoroutineScope, nav: NavController){
    var username by viewModel.username
    var password by viewModel.password
    var firstName by viewModel.firstName
    var lastName by viewModel.lastName

    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage
    val loginSuccess by viewModel.loginSuccess


    if (loginSuccess) {
        LaunchedEffect(key1 = Unit) {
            nav.navigate("home") {
                popUpTo("signup") { inclusive = true }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(300.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    focusedLabelColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White,
                    focusedLabelColor = Color.White,
                ),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White,
                focusedLabelColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White,
                focusedLabelColor = Color.White
            )
        )

        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.secondary,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))


        Button(
            onClick = { coroutineScope.launch { viewModel.createUser() }},
            enabled = !isLoading,
            modifier = Modifier.width(150.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
        ) {
            if (isLoading){
                Text("Loading...", color = MaterialTheme.colorScheme.secondary)
            }
            else{
                Text("Sign Up", color = Color.Black)}

        }

    }

}


