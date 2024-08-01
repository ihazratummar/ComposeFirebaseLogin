package com.example.composeloginapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.composeloginapp.auth.AuthViewModel
import com.example.composeloginapp.auth.screens.ForgotPasswordScreen
import com.example.composeloginapp.auth.screens.LoginScreen
import com.example.composeloginapp.auth.screens.SignUpScreen
import com.example.composeloginapp.presentation.NewScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    startDestination: String,
    authViewModel: AuthViewModel,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        authNav(navController, authViewModel)
        appNav(navController, authViewModel)
    }
}


fun NavGraphBuilder.authNav(navController: NavController, authViewModel: AuthViewModel) {
    navigation(
        route = Route.AuthNav.route,
        startDestination = Route.LoginScreen.route
    ) {
        composable(route = Route.LoginScreen.route) {
            LoginScreen(navController, authViewModel)
        }
        composable(route = Route.SignUpScreen.route) {
            SignUpScreen(navController, authViewModel)
        }
        composable(route = Route.ForgotPassword.route) {
            ForgotPasswordScreen(navController, authViewModel)
        }
    }
}

fun NavGraphBuilder.appNav(navController: NavController, authViewModel: AuthViewModel) {
    navigation(
        route = Route.AppNav.route,
        startDestination = Route.Home.route
    ) {
        composable(route = Route.Home.route) {
            AppNavigation(navController = navController, authViewModel = authViewModel )
        }
        composable(route = Route.NewScreen.route){
            NewScreen(navController = navController)
        }

    }
}