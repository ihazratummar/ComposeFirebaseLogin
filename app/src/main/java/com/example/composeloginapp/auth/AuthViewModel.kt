package com.example.composeloginapp.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeloginapp.auth.model.UserData
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {


    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Please fill all fields")
            return
        }
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value =
                        AuthState.Error(it.exception?.message ?: "Something went wrong")
                }
            }
    }
    fun signup(name: String , email: String, password: String, confirmPassword: String) {
        if (name.isEmpty() && email.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
            _authState.value = AuthState.Error("Please fill all fields")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value =
                        AuthState.Error(it.exception?.message ?: "Something went wrong")
                }
            }
    }

    fun signOut(){
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

}