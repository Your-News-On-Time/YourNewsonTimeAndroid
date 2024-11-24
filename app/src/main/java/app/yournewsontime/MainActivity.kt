package app.yournewsontime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import app.yournewsontime.ui.navigation.NavGraph
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()
        val startDestination = if (auth.currentUser != null) "feed" else "startPage"

        // La buena mierda -> println("User ID: ${auth.currentUser?.uid}")

        setContent {
            val navController = rememberNavController()
            NavGraph(navController = navController, startDestination = startDestination)
        }
    }
}