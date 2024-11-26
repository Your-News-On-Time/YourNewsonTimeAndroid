package app.yournewsontime.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.yournewsontime.screens.FeedScreen
import app.yournewsontime.screens.LoginScreen
import app.yournewsontime.screens.RegisterScreen
import app.yournewsontime.screens.StartPageScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.StartPageScreen.route) {
        composable(route = AppScreens.StartPageScreen.route) {
            StartPageScreen(navController)
        }
        composable(route = AppScreens.RegisterScreen.route) {
            RegisterScreen(navController)
        }
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = AppScreens.FeedScreen.route) {
            FeedScreen(navController)
        }
    }
}