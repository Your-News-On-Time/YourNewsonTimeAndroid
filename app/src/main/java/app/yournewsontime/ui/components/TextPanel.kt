package app.yournewsontime.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextPanel(onGetStartedClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(bottom = 45.dp, start = 16.dp, end = 16.dp, top = 50.dp)
    ) {
        Text(
            text = "Welcome to",
            fontSize = 26.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Title("Your News on Time")

        Text(
            text = "The best place to find out\nwhat's going on in the world",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 20.dp, bottom = 40.dp)
        )

        Button(
            "Get started",
            onClick = onGetStartedClick
        )
    }
}