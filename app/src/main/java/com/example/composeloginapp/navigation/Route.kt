package com.example.composeloginapp.navigation

sealed class Route(val route: String){
    object LoginScreen: Route(route = "loginScreen")

    object SignUpScreen: Route(route = "signupScreen")

    object AppNav: Route(route = "appNav")

    object Home: Route(route = "home")

    object Notification: Route(route = "notification")

    object Settings: Route(route = "settings")
}