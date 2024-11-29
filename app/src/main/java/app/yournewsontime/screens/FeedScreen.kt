package app.yournewsontime.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.yournewsontime.data.model.Article
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.navigation.AppScreens
import app.yournewsontime.ui.components.AlertDialog
import app.yournewsontime.ui.components.auth.LogoutButton
import app.yournewsontime.ui.view.main.ArticleItem
import app.yournewsontime.viewmodel.NewYorkTimesViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FeedScreen(
    navController: NavController,
    authRepository: FirebaseAuthRepository,
    viewModel: NewYorkTimesViewModel,
    apiKey: String
) {
    val currentUser = authRepository.getCurrentUser()

    val userNickname = if (currentUser?.isAnonymous == false) {
        currentUser.email?.split("@")?.get(0) ?: "Unknown"
    } else {
        "Guest"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hello, $userNickname!") },
                colors = TopAppBarColors(
                    titleContentColor = Color.Black,
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                    navigationIconContentColor = Color.Transparent,
                    actionIconContentColor = Color.Transparent,
                )
            )
        }

        ,
    ) { padding ->
        FeedBodyContent(
            navController,
            authRepository,
            viewModel,
            apiKey,
            padding
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FeedBodyContent(
    navController: NavController,
    authRepository: FirebaseAuthRepository,
    viewModel: NewYorkTimesViewModel,
    apiKey: String,
    padding: PaddingValues
) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val beginDate = LocalDate.now().minusDays(1).format(formatter)
    val endDate = LocalDate.now().format(formatter)
    val scope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val articles by viewModel.articles
    val error by viewModel.errorMessage

    LaunchedEffect(Unit) {
        viewModel.fetchArticles("technology", apiKey, beginDate, endDate)
    }

    Column {
        Spacer(modifier = Modifier.padding(30.dp))

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

        Box(modifier = Modifier.padding(padding)) {
            if (error != null) {
                Text("Error: $error", color = MaterialTheme.colorScheme.error)
            } else {
                LazyColumn {
                    items(articles) { article ->
                        ArticleItem(article)
                    }
                }
            }
        }

        errorMessage?.let {
            AlertDialog(message = it)
            errorMessage = null
        }

        // TODO Footer
    }

}

