package app.yournewsontime.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Splitter(orText: Boolean = false) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
            .padding(vertical = 16.dp)
    )

    if (orText) {
        Text(
            text = "Or",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.46f),
            fontSize = 16.sp,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
        )
    }
}