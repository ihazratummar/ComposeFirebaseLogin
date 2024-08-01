package com.example.composeloginapp.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composeloginapp.auth.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : ViewModel() {


    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    private val _userData = MutableStateFlow(UserData())
    val userData = _userData.asStateFlow()

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
            fetchUserData()
        }
    }

    private fun fetchUserData() {
        val userId = auth.currentUser?.uid ?: return
        fireStore.collection("user").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    _userData.value = document.toObject<UserData>()!!
                } else {
                    _userData.value = userData.value.copy(fullname = "", email = "")
                }
            }.addOnFailureListener { e ->
                _authState.value = AuthState.Error(e.message ?: "Something went wrong")
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

    fun signup(name: String, email: String, password: String, confirmPassword: String) {
        if (name.isEmpty() && email.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
            _authState.value = AuthState.Error("Please fill all fields")
            return
        }
        if (password != confirmPassword) {
            _authState.value = AuthState.Error("Password does not match")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    val userId = auth.currentUser?.uid ?: ""
                    val userData = UserData(
                        userId = user?.uid ?: "",
                        fullname = name,
                        email = email,
                        bio = null
                    )
                    fireStore.collection("user").document(userId)
                        .set(userData)
                        .addOnSuccessListener {
                            _authState.value = AuthState.Authenticated
                        }
                        .addOnFailureListener { e ->
                            _authState.value = AuthState.Error(e.message ?: "Something went wrong")
                        }
                } else {
                    _authState.value =
                        AuthState.Error(it.exception?.message ?: "Something went wrong")
                }
            }
    }

    fun updateProfile(userData: UserData) {
        val userId = auth.currentUser?.uid ?: return
        fireStore.collection("user").document(userId)
            .update(
                mapOf(
                    "fullname" to userData.fullname,
                    "bio" to userData.bio
                )
            )
            .addOnSuccessListener {
                _userData.value = userData // Update local state
            }
            .addOnFailureListener { e ->
                _authState.value = AuthState.Error(e.message ?: "Failed to update profile")
            }
    }

    fun resetPassword(email: String) {
        _authState.value = AuthState.Loading
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                _authState.value = AuthState.Unauthenticated
            }
            .addOnFailureListener { e ->
                _authState.value = AuthState.Error(e.message ?: "Failed to reset password")
            }
    }

    fun signOut() {
        auth.signOut()
        _userData.value = UserData()
        _authState.value = AuthState.Unauthenticated
    }
}