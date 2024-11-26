package app.yournewsontime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import app.yournewsontime.navigation.AppNavigation
import app.yournewsontime.ui.theme.YourNewsOnTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            YourNewsOnTimeTheme {
                Surface() {
                    AppNavigation()
                }
            }
        }
    }
}