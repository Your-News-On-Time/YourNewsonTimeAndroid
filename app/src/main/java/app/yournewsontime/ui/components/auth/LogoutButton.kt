package app.yournewsontime.ui.components.auth

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LogoutButton(
    onClick: () -> Unit,
    isLoggedIn: Boolean
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        if (isLoggedIn) {
            Text(text = "Logout")
        } else {
            Text(text = "Login")
        }
    }
}