package com.example.composeloginapp.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeloginapp.auth.AuthState
import com.example.composeloginapp.auth.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state = _state.asStateFlow()
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    private val _userData = MutableStateFlow(UserData())
    val userData = _userData.asStateFlow()


    init {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            fireStore.collection("users").document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    val userData = document.toObject<UserData>()
                    if (userData != null) {
                        _state.value = SettingState(
                            userData = UserData(
                                fullname = _userData.value.fullname,
                                email = _userData.value.email,
                                bio = _userData.value.bio
                            ),
                            newName = userData.fullname ?: "",
                            newBio = userData.bio ?: ""
                        )
                    }
                }
                .addOnFailureListener { e ->
                    _authState.value = AuthState.Error(e.message ?: "Failed to fetch user data")
                }
        }
        fetchUserData()
    }


    private fun fetchUserData() {
        val userId = auth.currentUser?.uid?: return
        fireStore.collection("user").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null){
                    _userData.value = document.toObject<UserData>()!!
                }else{
                    _userData.value = userData.value.copy(fullname = "", email = "" )
                }
            }.addOnFailureListener {e ->
                _authState.value = AuthState.Error(e.message ?: "Something went wrong")
            }
    }


    fun onEvent(event: SettingEvent) {
        when (event) {
            is SettingEvent.UpdateBio -> {
                _state.update { it.copy(newBio = event.bio) }
            }

            SettingEvent.UpdateDialog -> {
                _state.update { it.copy(isUpdating = !it.isUpdating) }
            }

            is SettingEvent.UpdateName -> {
                _state.update { it.copy(newName = event.name) }
            }

            is SettingEvent.UpdateProfile -> {
                val updateUser = event.userData.copy(
                    fullname = _state.value.newName,
                    bio = _state.value.newBio
                )

                val userId = auth.currentUser?.uid ?: return
                fireStore.collection("users").document(userId)
                    .update(
                        mapOf(
                            "fullname" to updateUser.fullname,
                            "bio" to updateUser.bio
                        )
                    )
                    .addOnSuccessListener {
                        _state.update { it.copy(userData = updateUser) }
                        _authState.value = AuthState.Authenticated
                    }
                    .addOnFailureListener { e ->
                        _authState.value = AuthState.Error(e.message ?: "Failed to update profile")
                    }
            }
        }

    }
}
