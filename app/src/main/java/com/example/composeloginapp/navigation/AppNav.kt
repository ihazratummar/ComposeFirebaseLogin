package com.example.composeloginapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.composeloginapp.auth.AuthState
import com.example.composeloginapp.auth.AuthViewModel
import com.example.composeloginapp.presentation.HomeScreen
import com.example.composeloginapp.presentation.NotificationScreen
import com.example.composeloginapp.presentation.SettingScreen

@Composable
fun AppNav(navController: NavController, authViewModel: AuthViewModel) {
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> {
                navController.navigate(Route.LoginScreen.route) {
                    popUpTo(Route.AppNav.route) { inclusive = true }
                }
            }

            else -> Unit
        }
    }
    val navItemList = listOf(
        NavItem("Home", Icons.Rounded.Home),
        NavItem("Notification", Icons.Rounded.Notifications, 99),
        NavItem("Setting", Icons.Rounded.Settings)
    )
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color.Transparent,
                tonalElevation = NavigationBarDefaults.Elevation
            ) {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = Color.Gray
                        ),
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            BadgedBox(badge = {
                                if (navItem.badge != null){
                                    Badge(){
                                        Text(text = navItem.badge.toString())
                                    }
                                }
                            }) {
                                Icon(imageVector = navItem.icon, contentDescription = navItem.title)
                            }
                        },
                        label = { navItem.title }
                    )
                }
            }
        }
    ) {
        ContentScreen(
            modifier = Modifier.padding(it),
            selectedIndex,
            navController,
            authViewModel
        )
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int, navController: NavController, authViewModel:AuthViewModel) {
    when(selectedIndex){
        0-> HomeScreen(navController = navController)
        1-> NotificationScreen(navController = navController)
        2-> SettingScreen(navController = navController, authViewModel)
    }
}