package app.yournewsontime.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.navigation.AppScreens
import app.yournewsontime.ui.components.AlertDialog
import app.yournewsontime.ui.components.auth.LogoutButton
import app.yournewsontime.ui.view.main.ArticlesScreen
import app.yournewsontime.viewModel.NYTimesViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FeedScreen(
    navController: NavController,
    authRepository: FirebaseAuthRepository,
    viewmodel: NYTimesViewModel,
    apiKey: String
) {
    Scaffold {
        FeedBodyContent(
            navController,
            authRepository,
            viewmodel,
            apiKey
        )
    }
}

@Composable
fun FeedBodyContent(
    navController: NavController,
    authRepository: FirebaseAuthRepository,
    viewmodel: NYTimesViewModel,
    apiKey: String
) {
    val scope = rememberCoroutineScope()
    val currentUser = authRepository.getCurrentUser()

    val userEmail = if (currentUser?.isAnonymous == false) {
        currentUser.email ?: "Unknown"
    } else {
        "Guest"
    }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column {
        // TODO Header

        Text(text = "Welcome, $userEmail!")

        Spacer(modifier = Modifier.height(16.dp))

        Column {
            ArticlesScreen(
                viewmodel = viewmodel,
                apiKey = apiKey
            )
        }

        LogoutButton(
            onClick = {
                scope.launch {
                    val result = authRepository.logout()
                    if (result.isSuccess) {
                        navController.navigate(route = AppScreens.LoginScreen.route) {
                            popUpTo(AppScreens.FeedScreen.route) { inclusive = true }
                        }
                    } else {
                        errorMessage = "Failed to log out: ${result.exceptionOrNull()?.message}"
                    }
                }
            },
            isAnonymous = authRepository.isUserAnonymous()
        )

        errorMessage?.let {
            AlertDialog(message = it)
            errorMessage = null
        }

        // TODO Footer
    }
}