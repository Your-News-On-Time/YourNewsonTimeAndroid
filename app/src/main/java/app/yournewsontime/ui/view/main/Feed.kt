package app.yournewsontime.ui.view.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.yournewsontime.ui.components.CardTest
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Feed(navController: NavController) {
    val auth = FirebaseAuth.getInstance()

    Column {
        // TODO Header

        Column {
            CardTest()
        }

        Button(
            onClick = {
                auth.signOut() // Cerrar sesión con Firebase
                navController.navigate("startPage") {
                    popUpTo("feed") {
                        inclusive = true
                    }
                }
            },
            enabled = auth.currentUser != null,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Logout")
        }

        // TODO Footer
    }
}