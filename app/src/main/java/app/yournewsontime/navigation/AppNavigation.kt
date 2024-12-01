package app.yournewsontime.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import app.yournewsontime.data.model.Article
import app.yournewsontime.data.repository.AppPreferencesRepository
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.screens.ArticleScreen
import app.yournewsontime.screens.FeedScreen
import app.yournewsontime.screens.LoginScreen
import app.yournewsontime.screens.ProfileScreen
import app.yournewsontime.screens.RegisterScreen
import app.yournewsontime.screens.SavedScreen
import app.yournewsontime.screens.StartScreen
import app.yournewsontime.viewmodel.GoogleLoginState
import app.yournewsontime.viewmodel.NewYorkTimesViewModel
import androidx.navigation.compose.NavHost as NavHost

@SuppressLint("NewApi")
@Composable
fun AppNavigation(
    navController: NavHostController,
    googleLoginState: GoogleLoginState,
    onGoogleSignIn: () -> Unit,
    authRepository: FirebaseAuthRepository,
    appPreferencesRepository: AppPreferencesRepository,
    startDestination: String,
    viewModel: NewYorkTimesViewModel,
    apiKey: String
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
                authRepository = authRepository,
                viewModel = viewModel,
                apiKey = apiKey
            )
        }

        /*composable(route = AppScreens.ArticleScreen.route) {
            ArticleScreen(
                navController = navController,
                authRepository = authRepository,
                viewModel = viewModel,
                apiKey = apiKey
            )
        }*/

        composable(route = "article_screen") {
            ArticleScreen(navController, authRepository, apiKey)
        }


        composable(route = AppScreens.ProfileScreen.route) {
            ProfileScreen()
        }

        composable(route = AppScreens.SavedScreen.route) {
            SavedScreen()
        }


    }
}