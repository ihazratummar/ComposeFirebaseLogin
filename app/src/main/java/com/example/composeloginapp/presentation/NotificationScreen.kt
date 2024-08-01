package com.example.composeloginapp.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composeloginapp.auth.AuthState
import com.example.composeloginapp.auth.AuthViewModel
import com.example.composeloginapp.navigation.Route

@Composable
fun NotificationScreen(navController: NavController, authViewModel: AuthViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val authState by authViewModel.authState.observeAsState()
    LaunchedEffect(authState) {
        when(authState){
            is AuthState.Unauthenticated -> {
                navController.navigate(Route.AuthNav.route){
                    popUpTo(Route.AppNav.route) { inclusive = true }
                }
            }
            is AuthState.Error -> {
                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_LONG).show()
            }
            else -> Unit
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Notification")
    }
}