package app.yournewsontime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import app.yournewsontime.data.repository.AppPreferencesRepository
import app.yournewsontime.data.repository.CategoryProvider
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.data.repository.NewYorkTimesRepository
import app.yournewsontime.data.repository.api.ApiClient
import app.yournewsontime.data.repository.api.NewYorkTimesApiService
import app.yournewsontime.navigation.AppNavigation
import app.yournewsontime.navigation.AppScreens
import app.yournewsontime.ui.theme.YourNewsOnTimeTheme
import app.yournewsontime.viewmodel.GoogleLoginViewModel
import app.yournewsontime.viewmodel.NewYorkTimesViewModel
import app.yournewsontime.viewmodel.NewYorkTimesViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var authRepository: FirebaseAuthRepository
    private lateinit var appPreferencesRepository: AppPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CategoryProvider.initializeCategories(this)
        authRepository = FirebaseAuthRepository(context = this)
        appPreferencesRepository = AppPreferencesRepository(context = this)

        val newYorkTimesApiKey = BuildConfig.NEW_YORK_TIMES_API_KEY

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
            val newYorkTimesRepository =
                NewYorkTimesRepository(ApiClient.retrofit.create(NewYorkTimesApiService::class.java))
            val newYorkTimesViewModel: NewYorkTimesViewModel =
                viewModel(factory = NewYorkTimesViewModelFactory(newYorkTimesRepository))

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
                        appPreferencesRepository = appPreferencesRepository,
                        viewModel = newYorkTimesViewModel,
                        apiKey = newYorkTimesApiKey
                    )
                }
            }
        }
    }
}
