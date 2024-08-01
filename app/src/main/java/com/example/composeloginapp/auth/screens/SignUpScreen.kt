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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
fun SignUpScreen(navController: NavController, authViewModel: AuthViewModel) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val isFormValid by remember {
        derivedStateOf {
            name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
        }
    }
    val isPasswordValid by remember {
        derivedStateOf {
            password == confirmPassword
        }
    }
    val context = LocalContext.current

    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> {
                navController.navigate(Route.AppNav.route) {
                    popUpTo(Route.SignUpScreen.route) { inclusive = true }
                }
            }
            is AuthState.Error -> {
                Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_LONG).show()
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
        Text(text = "Sign Up", fontSize = 40.sp, fontFamily = FontFamily(Font(R.font.nunitobold)))
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "First create your account",
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.nunitobold)),
            color = Color(0xFFA8A6A7)
        )
        Spacer(modifier = Modifier.height(100.dp))
        CustomTextField(
            label = { Text(text = "Full name") },
            placeholder = { Text(text = "Enter your full name") },
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.name),
                    contentDescription = null
                )
            },
            value = name,
            onValueChange = { name = it },
            keyboardtype = KeyboardType.Text,
            imeAction = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(5.dp))
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
            imeAction = ImeAction.Next,
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
        CustomTextField(
            label = { Text(text = "Confirm password") },
            placeholder = { Text(text = "Enter Your Password") },
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.passwordicon),
                    contentDescription = null
                )
            },
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            keyboardtype = KeyboardType.Password,
            imeAction = ImeAction.Done,
            trailingIcon = {
                val image =
                    if (confirmPasswordVisible) painterResource(id = R.drawable.showpassword)
                    else painterResource(id = R.drawable.hidepassowrd)
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        painter = image,
                        contentDescription = if (confirmPasswordVisible) "Hide Password" else "Show Password",
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xFFD87234)
                    )
                }
            },
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(2.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            if (confirmPassword.isNotBlank() && !isPasswordValid) {
                Icon(painter = painterResource(id = R.drawable.alert), contentDescription ="Alert", tint = MaterialTheme.colorScheme.error )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Password do not match", fontFamily = FontFamily(Font(R.font.nunitoregular)),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        Spacer(modifier = Modifier.height(35.dp))
        Button(
            onClick = {
                authViewModel.signup(name, email, password, confirmPassword)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                disabledContentColor = Color.Gray,
                disabledContainerColor = Color(0xFF222222)
            ),
            enabled = isFormValid && isPasswordValid && authState.value != AuthState.Loading
        ) {
            Text(
                text = "SIGN UP",
                fontFamily = FontFamily(Font(R.font.nunitoregular)),
                fontSize = 22.sp
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row {
            Text(
                text = "Already have an account?",
                fontFamily = FontFamily(Font(R.font.nunitoregular)),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "Sign In",
                fontFamily = FontFamily(Font(R.font.nunitoregular)),
                fontSize = 16.sp,
                color = Color(0xFFD87234),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    navController.navigate(Route.LoginScreen.route)
                }
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}