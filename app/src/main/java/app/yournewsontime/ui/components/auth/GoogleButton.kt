package app.yournewsontime.ui.components.auth

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import app.yournewsontime.ui.theme.GoogleButtonColor

@Composable
fun GoogleButton(onclick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onclick,
        colors = ButtonDefaults.buttonColors(
            containerColor = GoogleButtonColor,
            contentColor = Color.White
        ),
        modifier = modifier
    ) {
        Text("Google")
    }
}