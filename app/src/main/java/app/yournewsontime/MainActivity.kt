package app.yournewsontime

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
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
import app.yournewsontime.viewModel.GoogleLoginViewModel
import app.yournewsontime.viewModel.NewYorkTimesViewModel
import app.yournewsontime.viewModel.NewYorkTimesViewModelFactory

class MainActivity : FragmentActivity() {
    private lateinit var authRepository: FirebaseAuthRepository
    private lateinit var appPreferencesRepository: AppPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CategoryProvider.initializeCategories(this)
        authRepository = FirebaseAuthRepository(context = this)
        appPreferencesRepository = AppPreferencesRepository(context = this)

        val isBiometricEnabled = appPreferencesRepository.isBiometricEnabled()

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

        if (isBiometricEnabled && shouldPromptBiometricAuth()) {
            promptBiometricAuthentication {
                launchApp(
                    newYorkTimesApiKey,
                    startDestination,
                    googleLoginViewModel,
                    googleSignInLauncher
                )
            }
        } else {
            launchApp(
                newYorkTimesApiKey,
                startDestination,
                googleLoginViewModel,
                googleSignInLauncher
            )
        }
    }

    private fun shouldPromptBiometricAuth(): Boolean {
        return authRepository.isUserLoggedIn() && isBiometricAvailable()
    }

    private fun isBiometricAvailable(): Boolean {
        val biometricManager = BiometricManager.from(this)
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) ==
                BiometricManager.BIOMETRIC_SUCCESS
    }

    private fun promptBiometricAuthentication(onSuccess: () -> Unit) {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(
            this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    finish()
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Please authenticate to proceed")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun launchApp(
        newYorkTimesApiKey: String,
        startDestination: String,
        googleLoginViewModel: GoogleLoginViewModel,
        googleSignInLauncher: ActivityResultLauncher<Intent>
    ) {
        setContent {
            val navController = rememberNavController()
            val googleLoginState = googleLoginViewModel.googleLoginState.collectAsState()
            val newYorkTimesRepository =
                NewYorkTimesRepository(ApiClient.retrofit.create(NewYorkTimesApiService::class.java))
            val newYorkTimesViewModel: NewYorkTimesViewModel =
                viewModel(factory = NewYorkTimesViewModelFactory(newYorkTimesRepository))

            YourNewsOnTimeTheme {
                Surface {
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
