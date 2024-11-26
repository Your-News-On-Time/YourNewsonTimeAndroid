package app.yournewsontime.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import app.yournewsontime.navigation.AppScreens
import app.yournewsontime.ui.components.CardTest
import app.yournewsontime.ui.components.auth.LogoutButton
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FeedScreen(navController: NavController) {
    Scaffold {
        FeedBodyContent(navController)
    }
}

@Composable
fun FeedBodyContent(navController: NavController) {
    val scope = rememberCoroutineScope()

    Column {
        // TODO Header

        Column {
            CardTest()
        }

        LogoutButton(
            onClick = {
                scope.launch {
                    // TODO: Implement logout
                    navController.navigate(route = AppScreens.LoginScreen.route)
                }
            }
        )

        // TODO Footer
    }
}