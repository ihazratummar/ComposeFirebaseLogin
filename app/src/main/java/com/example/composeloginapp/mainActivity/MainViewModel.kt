package com.example.composeloginapp.mainActivity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.composeloginapp.navigation.Route

class MainViewModel: ViewModel() {

    private val _startDestination = mutableStateOf(Route.LoginScreen.route)
    val startDestination: State<String> = _startDestination

}