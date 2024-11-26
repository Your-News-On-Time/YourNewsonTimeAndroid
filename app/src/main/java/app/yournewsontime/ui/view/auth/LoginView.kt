package app.yournewsontime.ui.view.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.yournewsontime.data.repository.AuthRepository
import app.yournewsontime.ui.components.PrincipalButton
import app.yournewsontime.ui.components.Splitter
import app.yournewsontime.ui.components.auth.AnonymouslyButton
import app.yournewsontime.ui.components.auth.GoogleButton
import app.yournewsontime.ui.theme.interFontFamily
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun LoginView(
    navController: NavController,
    authRepository: AuthRepository = AuthRepository(FirebaseAuth.getInstance())
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(top = 45.dp)
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier.height(50.dp)
        )

        Text(
            text = "Log in",
            fontSize = 30.sp,
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(50.dp)
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
            modifier = Modifier.height(32.dp)
        )

        PrincipalButton(
            "Log in",
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    errorMessage = "Please fill in all fields"
                    return@PrincipalButton
                }
                scope.launch {
                    val result = authRepository.loginWithEmailAndPassword(email, password)
                    if (result.isSuccess) {
                        navController.navigate("feed")
                    } else {
                        errorMessage = result.exceptionOrNull()?.message
                    }
                }
            }
        )

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        TextButton(
            onClick = { navController.navigate("register") }
        ) {
            Text(
                text = "Don't have an account? Register",
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
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            AnonymouslyButton(
                onclick = {
                    navController.navigate("feed")
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}