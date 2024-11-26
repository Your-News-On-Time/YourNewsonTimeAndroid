package app.yournewsontime.ui.view.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.yournewsontime.R
import app.yournewsontime.ui.components.BottomSheetContent
import app.yournewsontime.ui.components.TextPanel
import app.yournewsontime.ui.theme.Branding_YourNewsOnTime_Background
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartPageView(navController: NavController) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Branding_YourNewsOnTime_Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(
            modifier = Modifier
                .height(50.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.start_page_icons),
            contentDescription = "Start Page Icons",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
        )
        TextPanel(
            onGetStartedClick = {
                showBottomSheet = true
            }
        )
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            BottomSheetContent(
                onDismiss = {
                    scope.launch {
                        sheetState.hide()
                        showBottomSheet = false
                    }
                },
                navigateToFeed = {
                    scope.launch {
                        sheetState.hide()
                        showBottomSheet = false
                        navController.navigate("feed")
                    }
                },
                navigateToRegister = {
                    scope.launch {
                        sheetState.hide()
                        showBottomSheet = false
                        navController.navigate("register")
                    }
                },
                googleSignUp = {
                    // TODO Handle Google Sign In
                }
            )
        }
    }
}