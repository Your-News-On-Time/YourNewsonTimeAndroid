package app.yournewsontime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import app.yournewsontime.data.repository.AppPreferencesRepository
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.navigation.AppNavigation
import app.yournewsontime.navigation.AppScreens
import app.yournewsontime.ui.theme.YourNewsOnTimeTheme
import app.yournewsontime.viewmodel.GoogleLoginViewModel

class MainActivity : ComponentActivity() {
    private lateinit var authRepository: FirebaseAuthRepository
    private lateinit var appPreferencesRepository: AppPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authRepository = FirebaseAuthRepository(context = this)
        appPreferencesRepository = AppPreferencesRepository(context = this)

        val startDestination = if (appPreferencesRepository.isFirstLaunch()) {
            AppScreens.StartScreen.route
        } else if (authRepository.isUserLoggedIn()) {
            AppScreens.FeedScreen.route
        } else {
            AppScreens.LoginScreen.route
        }

        val googleLoginViewModel = GoogleLoginViewModel(authRepository)
        val googleSignInLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            googleLoginViewModel.loginWithGoogle(result.data)
        }

        setContent {
            val navController = rememberNavController()
            val googleLoginState = googleLoginViewModel.googleLoginState.collectAsState()

            YourNewsOnTimeTheme {
                Surface() {
                    AppNavigation(
                        navController = navController,
                        googleLoginState = googleLoginState.value,
                        onGoogleSignIn = {
                            val intent = googleLoginViewModel.getGoogleSignInIntent(this)
                            googleSignInLauncher.launch(intent)
                        },
                        authRepository = authRepository,
                        startDestination = startDestination,
                        appPreferencesRepository = appPreferencesRepository
                    )
                }
            }
        }
    }
}