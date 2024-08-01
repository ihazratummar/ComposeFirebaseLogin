package com.example.composeloginapp.presentation.profile

import com.example.composeloginapp.auth.model.UserData

/**
 * @author Hazrat Ummar Shaikh
 */

sealed interface SettingEvent {

    data class UpdateName(val name: String) : SettingEvent
    data class UpdateBio(val bio: String) : SettingEvent
    data class UpdateProfile(val userData: UserData): SettingEvent
    data object UpdateDialog : SettingEvent
}