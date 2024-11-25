package app.yournewsontime.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.yournewsontime.ui.view.auth.LoginView
import app.yournewsontime.ui.view.auth.RegisterView
import app.yournewsontime.ui.view.main.FeedView
import app.yournewsontime.ui.view.main.StartPageView

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("startPage") { StartPageView(navController) }
        composable("feed") { FeedView(navController) }
        composable("register") { RegisterView(navController) }
        composable("login") { LoginView(navController = navController) }
    }
}