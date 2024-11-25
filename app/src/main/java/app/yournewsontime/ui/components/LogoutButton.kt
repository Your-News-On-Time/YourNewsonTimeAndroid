package app.yournewsontime.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LogoutButton(navController: NavController, auth: FirebaseAuth) {
    Button(
        onClick = {
            auth.signOut()
            navController.navigate("login") {
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
}