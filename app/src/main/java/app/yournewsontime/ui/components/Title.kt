package app.yournewsontime.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import app.yournewsontime.ui.theme.Branding_YourNewsOnTime
import app.yournewsontime.ui.theme.interFontFamily

@Composable
fun Title(text: String) {
    Text(
        text = text,
        fontSize = 30.sp,
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Bold,
        color = Branding_YourNewsOnTime,
    )
}