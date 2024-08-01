package com.example.composeloginapp.auth.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composeloginapp.R
import com.example.composeloginapp.auth.AuthState
import com.example.composeloginapp.auth.AuthViewModel
import com.example.composeloginapp.auth.screens.components.CustomTextField
import com.example.composeloginapp.auth.screens.components.Eclipse
import com.example.composeloginapp.navigation.Route

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val isFormValid by remember {
        derivedStateOf {
            email.isNotBlank() && password.isNotBlank() && password.length >= 8
        }
    }
    val context = LocalContext.current
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                navController.navigate(Route.AppNav.route) {
                    popUpTo(Route.LoginScreen.route) { inclusive = true }
                }
            }

            is AuthState.Error -> {
                Toast.makeText(
                    context,
                    (authState.value as AuthState.Error).message,
                    Toast.LENGTH_LONG
                ).show()
            }

            else -> Unit
        }
    }
    Eclipse()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(text = "Sign In", fontSize = 40.sp, fontFamily = FontFamily(Font(R.font.nunitobold)))
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Enter your email and password",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.nunitobold)),
            color = Color(0xFFA8A6A7)
        )
        Spacer(modifier = Modifier.height(100.dp))
        CustomTextField(
            label = { Text(text = "Email") },
            placeholder = { Text(text = "Enter Your Email") },
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.email),
                    contentDescription = null
                )
            },
            value = email,
            onValueChange = { email = it },
            keyboardtype = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(5.dp))
        CustomTextField(
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Enter Your Password") },
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.passwordicon),
                    contentDescription = null
                )
            },
            value = password,
            onValueChange = { password = it },
            keyboardtype = KeyboardType.Password,
            imeAction = ImeAction.Done,
            trailingIcon = {
                val image = if (passwordVisible) painterResource(id = R.drawable.showpassword)
                else painterResource(id = R.drawable.hidepassowrd)
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = image,
                        contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xFFD87234)
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(5.dp))
        TextButton(onClick = { navController.navigate(route = Route.ForgotPassword.route)}) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ){
                Icon(painter =  painterResource(id = R.drawable.alert), contentDescription = "Forgot password")
                Text(text = "Forgot Password", color = MaterialTheme.colorScheme.error)
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {
                authViewModel.login(email, password)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,

                ),
            enabled = isFormValid && authState.value != AuthState.Loading
        ) {
            Text(
                text = "LOGIN",
                fontFamily = FontFamily(Font(R.font.nunitoregular)),
                fontSize = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(15.dp))
        Row {
            Text(
                text = "Don't have an account?",
                fontFamily = FontFamily(Font(R.font.nunitoregular)),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "Sign Up",
                fontFamily = FontFamily(Font(R.font.nunitoregular)),
                fontSize = 16.sp,
                color = Color(0xFFD87234),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navController.navigate(Route.SignUpScreen.route)
                }
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            HorizontalDivider(color = Color(0xFFA8A6A7), modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "Sign In With",
                fontFamily = FontFamily(Font(R.font.nunitoregular)),
                fontSize = 16.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(2.dp))
            HorizontalDivider(color = Color(0xFFA8A6A7), modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.grommet_icons_google),
                contentDescription = "google",
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Icon(
                painter = painterResource(id = R.drawable.logos_facebook),
                contentDescription = "google",
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))

    }
}

