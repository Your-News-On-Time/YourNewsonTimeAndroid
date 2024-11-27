package app.yournewsontime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.navigation.AppNavigation
import app.yournewsontime.ui.theme.YourNewsOnTimeTheme
import app.yournewsontime.viewmodel.GoogleLoginViewModel

class MainActivity : ComponentActivity() {
    private lateinit var authRepository: FirebaseAuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authRepository = FirebaseAuthRepository(context = this)

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
                    )
                }
            }
        }
    }
}