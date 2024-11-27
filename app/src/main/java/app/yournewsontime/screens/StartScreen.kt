package app.yournewsontime.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.yournewsontime.R
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.navigation.AppScreens
import app.yournewsontime.ui.components.PrincipalButton
import app.yournewsontime.ui.components.Title
import app.yournewsontime.ui.theme.Branding_YourNewsOnTime_Background
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StartScreen(
    navController: NavController,
    onGoogleSignIn: () -> Unit
) {
    Scaffold {
        StartBodyContent(navController, onGoogleSignIn)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartBodyContent(
    navController: NavController,
    onGoogleSignIn: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current
    val authRepository = remember { FirebaseAuthRepository(context) }

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
                scope.launch {
                    showBottomSheet = true
                }
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
                navigateToRegister = {
                    scope.launch {
                        sheetState.hide()
                        showBottomSheet = false
                        navController.navigate(route = AppScreens.RegisterScreen.route)
                    }
                },
                signWithGoogle = onGoogleSignIn,
                navigateToFeedAsGuest = {
                    scope.launch {
                        val result = authRepository.loginAnonymously()
                        if (result.isSuccess) {
                            sheetState.hide()
                            showBottomSheet = false
                            navController.navigate(AppScreens.FeedScreen.route) {
                                popUpTo(AppScreens.StartScreen.route) { inclusive = true }
                            }
                        }
                    }
                }
            )
        }
    }
}

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

        PrincipalButton(
            "Get started",
            onClick = onGetStartedClick
        )
    }
}

@Composable
fun BottomSheetContent(
    onDismiss: () -> Unit = {},
    navigateToRegister: () -> Unit = {},
    signWithGoogle: () -> Unit = {},
    navigateToFeedAsGuest: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign up",
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

        PrincipalButton(
            "Continue with Email",
            onClick = navigateToRegister
        )

        PrincipalButton(
            "Continue with Google",
            onClick = signWithGoogle,
        )

        Button(
            onClick = navigateToFeedAsGuest,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent
            ),
            modifier = Modifier
                .padding(top = 8.dp, bottom = 30.dp)
        ) {
            Text(
                text = "Continue as Guest",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
        }
    }
}