package com.example.composeloginapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeloginapp.auth.AuthViewModel
import com.example.composeloginapp.auth.screens.LoginScreen
import com.example.composeloginapp.auth.screens.SignUpScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier, startDestination: String, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination ){
        composable(route = Route.LoginScreen.route){
            LoginScreen(navController, authViewModel)
        }
        composable(route = Route.SignUpScreen.route){
            SignUpScreen(navController, authViewModel)
        }
        composable(route = Route.AppNav.route){
            AppNav(navController, authViewModel)
        }
    }
}