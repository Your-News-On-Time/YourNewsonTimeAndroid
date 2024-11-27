package app.yournewsontime.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.screens.FeedScreen
import app.yournewsontime.screens.LoginScreen
import app.yournewsontime.screens.RegisterScreen
import app.yournewsontime.screens.StartScreen

@Composable
fun AppNavigation(onGoogleSignIn: () -> Unit) {
    val navController = rememberNavController()
    val authRepository = FirebaseAuthRepository(context = LocalContext.current)

    val startDestination = remember {
        try {
            if (authRepository.isUserLoggedIn()) {
                AppScreens.FeedScreen.route
            } else {
                AppScreens.StartScreen.route
            }
        } catch (e: Exception) {
            // Fallback to StartScreen in case of an error
            AppScreens.StartScreen.route
        }
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = AppScreens.StartScreen.route) {
            StartScreen(navController, onGoogleSignIn)
        }
        composable(route = AppScreens.RegisterScreen.route) {
            RegisterScreen(navController, onGoogleSignIn)
        }
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(navController, onGoogleSignIn)
        }
        composable(route = AppScreens.FeedScreen.route) {
            FeedScreen(navController, authRepository)
        }
        /*composable(
            route = AppScreens.ArticleScreen.route + "/{articleId}",
            arguments = listOf(navArgument(name = "articleId") {
                type = NavType.StringType
            })
        ) {
            val articleId = it.arguments?.getString("articleId")
        }*/
    }
}