package app.yournewsontime.ui.view.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import app.yournewsontime.data.repository.AuthRepository
import app.yournewsontime.ui.components.CardTest
import app.yournewsontime.ui.components.auth.LogoutButton
import com.google.firebase.auth.FirebaseAuth

@Composable
fun FeedView(navController: NavController) {
    val authRepository = AuthRepository(FirebaseAuth.getInstance())

    Column {
        // TODO Header

        Text(
            text = "${authRepository.getCurrentUser()?.email}, ${authRepository.getCurrentUser()?.uid}",
        )

        Column {
            CardTest()
        }

        LogoutButton(navController, authRepository)

        // TODO Footer
    }
}