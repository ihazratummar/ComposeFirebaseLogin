package com.example.composeloginapp.mainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.composeloginapp.auth.AuthViewModel
import com.example.composeloginapp.navigation.NavGraph
import com.example.composeloginapp.navigation.Route
import com.example.composeloginapp.ui.theme.ComposeLoginAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLoginAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavGraph(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        startDestination = Route.LoginScreen.route,
                        authViewModel = viewModel
                    )
                }
            }
        }
    }
}
