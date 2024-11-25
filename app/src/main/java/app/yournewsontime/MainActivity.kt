package app.yournewsontime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import app.yournewsontime.data.repository.AuthRepository
import app.yournewsontime.ui.navigation.NavGraph
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authRepository = AuthRepository(FirebaseAuth.getInstance())
        val startDestination = if (authRepository.isUserLoggedIn()) "feed" else "startPage"

        setContent {
            val navController = rememberNavController()
            NavGraph(navController = navController, startDestination = startDestination)
        }
    }
}