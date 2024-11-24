package app.yournewsontime.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomSheetContent(
    onDismiss: () -> Unit,
    navigateToFeed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Log in / Sign up",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Please select your preferred method\nto continue setting up your account",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Button(
            "Continue with Email",
            onClick = {
                println("Email button clicked")
            }
        )

        Button(
            "Continue with Google",
            onClick = {
                println("Google button clicked")
            }
        )

        Button(
            onClick = {
                navigateToFeed()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent
            ),
            modifier = Modifier
                .padding(top = 8.dp, bottom = 30.dp)
        ) {
            Text(
                text = "Continue anonymously",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        }
    }
}