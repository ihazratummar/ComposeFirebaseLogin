package com.example.composeloginapp.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val title: String,
    val icon: ImageVector,
    val badge: Int? = null
)

