package com.example.composeloginapp.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composeloginapp.auth.AuthState
import com.example.composeloginapp.auth.AuthViewModel
import com.example.composeloginapp.auth.model.UserData
import com.example.composeloginapp.navigation.Route

/**
 * @author Hazrat Ummar Shaikh
 */

@Composable
fun NewScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val userData by authViewModel.userData.collectAsState()

    var updateName by remember {
        mutableStateOf(userData.fullname ?: "")
    }
    var updateBio by remember {
        mutableStateOf(userData.bio ?: "")
    }

    LaunchedEffect(userData) {
        updateName = userData.fullname ?: ""
        updateBio = userData.bio ?: ""
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.imePadding(),
                onClick = {
                authViewModel.updateProfile(
                    userData = UserData(
                        fullname = updateName,
                        bio = updateBio
                    )
                )
                navController.popBackStack()
            }) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Done")
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Text(text = userData.fullname.toString())
            TextField(
                value = updateName,
                onValueChange = { updateName = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = updateBio,
                onValueChange = { updateBio = it },
                label = { Text("Bio") },
                modifier = Modifier.fillMaxWidth()
            )

        }
    }
}