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
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.data.repository.NYTimesRepository
import app.yournewsontime.data.repository.api.ApiClient
import app.yournewsontime.data.repository.api.NYTimesApiService
import app.yournewsontime.navigation.AppNavigation
import app.yournewsontime.navigation.AppScreens
import app.yournewsontime.ui.theme.YourNewsOnTimeTheme
import app.yournewsontime.viewModel.NYTimesViewModel
import app.yournewsontime.viewModel.NYTimesViewModelFactory
import app.yournewsontime.viewmodel.GoogleLoginViewModel

class MainActivity : ComponentActivity() {
    private lateinit var authRepository: FirebaseAuthRepository
    private lateinit var appPreferencesRepository: AppPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authRepository = FirebaseAuthRepository(context = this)
        appPreferencesRepository = AppPreferencesRepository(context = this)

        // TODO: Remove API key from code and load from secure location
        val newYorkTimesApiKey = getString(R.string.ny_times_api_key)

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
                NYTimesRepository(ApiClient.retrofit.create(NYTimesApiService::class.java))
            val newYorkTimesViewModel: NYTimesViewModel =
                viewModel(factory = NYTimesViewModelFactory(newYorkTimesRepository))

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
                        viewmodel = newYorkTimesViewModel,
                        apiKey = newYorkTimesApiKey
                    )
                }
            }
        }
    }
}
