package com.example.composeloginapp.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composeloginapp.auth.AuthState
import com.example.composeloginapp.auth.AuthViewModel
import com.example.composeloginapp.auth.model.UserData
import com.example.composeloginapp.navigation.Route

@Composable
fun SettingScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val userData by authViewModel.userData.collectAsState()
    val context = LocalContext.current
    val authState by authViewModel.authState.observeAsState()

    LaunchedEffect(authState) {
        authViewModel.checkAuthStatus()
        when (authState) {
            is AuthState.Unauthenticated -> {
                navController.navigate(Route.AuthNav.route)
            }

            is AuthState.Error -> {
                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_LONG)
                    .show()
            }

            else -> Unit
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle, contentDescription = "Account",
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = userData.fullname ?: "Name",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = userData.bio ?: "Bio",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { navController.navigate(Route.NewScreen.route) }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
            }
            Button(onClick = { authViewModel.signOut() }) {
                Text(text = "Sign Out")
            }

        }
    }
}
