package app.yournewsontime.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.yournewsontime.R
import app.yournewsontime.ui.theme.Red_YourNewsonTime

@Composable
fun Title(text: String) {
    val interFontFamily = FontFamily(
        Font(R.font.inter_regular, weight = FontWeight.Normal)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = text,
            fontSize = 36.sp,
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Bold,
            color = Red_YourNewsonTime,
            modifier = Modifier.padding(16.dp)
        )
    }
}