package app.yournewsontime.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import app.yournewsontime.data.repository.AppPreferencesRepository
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.screens.ArticleScreen
import app.yournewsontime.screens.FeedScreen
import app.yournewsontime.screens.LoginScreen
import app.yournewsontime.screens.ProfileScreen
import app.yournewsontime.screens.RegisterScreen
import app.yournewsontime.screens.SavedScreen
import app.yournewsontime.screens.StartScreen
import app.yournewsontime.viewModel.GoogleLoginState
import app.yournewsontime.viewModel.NewYorkTimesViewModel

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

        composable(
            route = AppScreens.ArticleScreen.route + "/{articleId}",
            arguments = listOf(navArgument("articleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val articleId = requireNotNull(backStackEntry.arguments?.getString("articleId"))
            ArticleScreen(
                navController = navController,
                authRepository = authRepository,
                articleId = articleId,
                viewModel = viewModel
            )

        }


        composable(route = AppScreens.ProfileScreen.route) {
            ProfileScreen(
                navController = navController,
                authRepository = authRepository
            )
        }

        composable(route = AppScreens.SavedScreen.route) {
            SavedScreen(
                navController = navController,
                authRepository = authRepository
            )
        }


    }
}