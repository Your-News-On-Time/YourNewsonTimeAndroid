package app.yournewsontime.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.yournewsontime.data.repository.AppPreferencesRepository
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.screens.FeedScreen
import app.yournewsontime.screens.LoginScreen
import app.yournewsontime.screens.RegisterScreen
import app.yournewsontime.screens.StartScreen
import app.yournewsontime.viewmodel.GoogleLoginState

@Composable
fun AppNavigation(
    navController: NavHostController,
    googleLoginState: GoogleLoginState,
    onGoogleSignIn: () -> Unit,
    authRepository: FirebaseAuthRepository,
    appPreferencesRepository: AppPreferencesRepository,
    startDestination: String
) {
    if (googleLoginState is GoogleLoginState.Success) {
        navController.navigate(AppScreens.FeedScreen.route) {
            popUpTo(startDestination) {
                inclusive = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = AppScreens.StartScreen.route) {
            StartScreen(
                navController = navController,
                onGoogleSignIn = onGoogleSignIn,
                onFinishStartScreen = {
                    appPreferencesRepository.setFirstLaunch(false)
                }
            )
        }

        composable(route = AppScreens.RegisterScreen.route) {
            RegisterScreen(
                navController = navController,
                onGoogleSignIn = onGoogleSignIn
            )
        }

        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(
                navController = navController,
                onGoogleSignIn = onGoogleSignIn
            )
        }

        composable(route = AppScreens.FeedScreen.route) {
            FeedScreen(
                navController = navController,
                authRepository = authRepository
            )
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