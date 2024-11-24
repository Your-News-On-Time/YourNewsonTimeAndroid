package app.yournewsontime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import app.yournewsontime.ui.navigation.NavGraph
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            // TODO Load the start page the first time the app is opened
            NavGraph(navController = navController)
        }
    }
}