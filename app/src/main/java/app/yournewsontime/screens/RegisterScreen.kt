package app.yournewsontime.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.navigation.AppScreens
import app.yournewsontime.ui.components.AlertDialog
import app.yournewsontime.ui.components.PrincipalButton
import app.yournewsontime.ui.components.Splitter
import app.yournewsontime.ui.components.auth.GoogleButton
import app.yournewsontime.ui.components.auth.GuestButton
import app.yournewsontime.ui.theme.interFontFamily
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreen(
    navController: NavController,
    onGoogleSignIn: () -> Unit
) {
    Scaffold {
        RegisterBodyContent(navController, onGoogleSignIn)
    }
}

@Composable
fun RegisterBodyContent(navController: NavController, onGoogleSignIn: () -> Unit) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val authRepository = remember { FirebaseAuthRepository(context) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .padding(top = 45.dp)
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .height(50.dp)
        )

        Text(
            text = "Register with email",
            fontSize = 30.sp,
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier
                .height(50.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        PrincipalButton(
            "Sign up",
            onClick = {
                scope.launch {
                    if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                        errorMessage = "Please fill in all fields"
                        return@launch
                    }

                    if (password != confirmPassword) {
                        errorMessage = "Passwords do not match"
                        return@launch
                    }
                    try {
                        val result = authRepository.registerWithEmail(email, password)
                        if (result.isFailure) {
                            errorMessage = result.exceptionOrNull()?.message
                            return@launch
                        }
                        navController.navigate(route = AppScreens.FeedScreen.route)
                    } catch (e: Exception) {
                        errorMessage = e.message
                    }
                }
            }
        )

        errorMessage?.let {
            AlertDialog(message = it)
            errorMessage = null
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        TextButton(
            onClick = {
                scope.launch {
                    navController.navigate(route = AppScreens.LoginScreen.route)
                }
            }
        ) {
            Text(
                text = "Already have an account? Log in",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp
            )
        }

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Splitter(orText = true)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GoogleButton(
                onclick = onGoogleSignIn,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            GuestButton(
                onclick = {
                    scope.launch {
                        val result = authRepository.loginAnonymously()
                        if (result.isSuccess) {
                            navController.navigate(AppScreens.FeedScreen.route) {
                                popUpTo(AppScreens.StartScreen.route) { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}