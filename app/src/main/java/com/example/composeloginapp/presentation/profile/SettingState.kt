package com.example.composeloginapp.presentation.profile

import com.example.composeloginapp.auth.model.UserData

data class SettingState(
    val userData: UserData? = null,
    val isUpdating: Boolean = false,
    val updateError: String? = null,
    val newName: String = "",
    val newBio: String = ""
)
