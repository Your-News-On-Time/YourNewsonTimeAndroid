package app.yournewsontime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Surface
import androidx.lifecycle.lifecycleScope
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.navigation.AppNavigation
import app.yournewsontime.ui.theme.YourNewsOnTimeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var authRepository: FirebaseAuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authRepository = FirebaseAuthRepository(context = this)

        val googleSignInLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            lifecycleScope.launch {
                val loginResult = authRepository.loginWithGoogle(result.data)
                if (loginResult.isSuccess) {
                    println("Google Sign-In Success")
                    // TODO: Navigate to the FeedScreen
                } else {
                    println("Google Sign-In Failed: ${loginResult.exceptionOrNull()?.message}")
                }
            }
        }

        setContent {
            YourNewsOnTimeTheme {
                Surface() {
                    AppNavigation(
                        onGoogleSignIn = {
                            val intent = authRepository.getGoogleSignInIntent()
                            googleSignInLauncher.launch(intent)
                        }
                    )
                }
            }
        }
    }
}