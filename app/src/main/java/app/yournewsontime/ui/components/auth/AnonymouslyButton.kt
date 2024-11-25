package app.yournewsontime.ui.components.auth

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AnonymouslyButton(onclick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onclick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Gray,
            contentColor = Color.White
        ),
        modifier = modifier
    ) {
        Text("Anonymous")
    }
}