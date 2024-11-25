package app.yournewsontime.ui.view.main

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import app.yournewsontime.ui.components.CardTest
import app.yournewsontime.ui.components.LogoutButton
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Feed(navController: NavController) {
    val auth = FirebaseAuth.getInstance()

    Column {
        // TODO Header

        Column {
            CardTest()
        }

        LogoutButton(navController, auth)

        // TODO Footer
    }
}