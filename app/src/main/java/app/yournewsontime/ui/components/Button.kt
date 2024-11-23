package app.yournewsontime.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.yournewsontime.R
import app.yournewsontime.ui.theme.Branding_YourNewsOnTime
import app.yournewsontime.ui.theme.interFontFamily

@Composable
fun Button(text: String, onClick: () -> Unit) {
    androidx.compose.material3.Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Branding_YourNewsOnTime),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(10.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontFamily = interFontFamily,
            color = Color.White,
            modifier = Modifier.padding(5.dp),
        )
    }
}