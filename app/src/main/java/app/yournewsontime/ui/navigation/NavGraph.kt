package app.yournewsontime.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.yournewsontime.ui.view.Feed
import app.yournewsontime.ui.view.StartPage

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "startPage"
    ) {
        composable("startPage") { StartPage(navController) }
        composable("feed") { Feed() }
    }
}