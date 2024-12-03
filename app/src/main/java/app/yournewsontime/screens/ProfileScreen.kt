package app.yournewsontime.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.yournewsontime.R
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.ui.components.DrawerContent
import app.yournewsontime.ui.components.Footer
import app.yournewsontime.ui.theme.Branding_YourNewsOnTime
import app.yournewsontime.ui.theme.interFontFamily
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    authRepository: FirebaseAuthRepository
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var isMenuOpen by remember { mutableStateOf(false) }

    LaunchedEffect(drawerState.isOpen) {
        isMenuOpen = drawerState.isOpen
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navController,
                authRepository
            )
        },
        scrimColor = Color.Transparent
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Profile",
                            color = Color.Gray.copy(alpha = 0.9f)
                        )
                    },
                    colors = TopAppBarColors(
                        titleContentColor = Color.Black,
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        navigationIconContentColor = Color.Transparent,
                        actionIconContentColor = Color.Transparent
                    )
                )
            },
            bottomBar = {
                Footer(
                    navController = navController,
                    authRepository = authRepository,
                    modifier = Modifier.fillMaxWidth(),
                    onMenuClick = {
                        scope.launch {
                            if (drawerState.isClosed) {
                                drawerState.open()
                            } else {
                                drawerState.close()
                            }
                            drawerState.open()
                        }
                    },
                    isMenuOpen = isMenuOpen
                )
            }
        ) { padding ->
            ProfileBodyContent(
                navController,
                authRepository,
                padding
            )
        }
    }
}

@Composable
fun ProfileBodyContent(
    navController: NavController,
    authRepository: FirebaseAuthRepository,
    padding: PaddingValues
) {
    val currentUser = authRepository.getCurrentUser()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp)
                .background(Branding_YourNewsOnTime, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(18.dp))

                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .width(70.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "PRESS",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontFamily = interFontFamily,
                    fontSize = 60.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (
                    authRepository.isUserLoggedIn() &&
                    !authRepository.isUserAnonymous() &&
                    currentUser?.photoUrl != null
                ) {
                    AsyncImage(
                        model = currentUser.photoUrl,
                        contentDescription = "Profile Picture of ${currentUser.displayName}",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(
                            id = R.drawable.filled_account_icon
                        ),
                        contentDescription = "Profile Icon",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = if (authRepository.isUserAnonymous()) "Guest" else currentUser?.displayName
                        ?: "Unknown",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontFamily = interFontFamily,
                    fontSize = 45.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = if (authRepository.isUserAnonymous()) "guest@guest.guest" else currentUser?.email
                        ?: "No email",
                    color = Color.Black,
                    fontSize = 25.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (
                    authRepository.isUserLoggedIn() &&
                    !authRepository.isUserAnonymous()
                ) {
                    if (currentUser?.photoUrl != null) {
                        Text(
                            text = "Google",
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    } else {
                        Text(
                            text = "Email & Password",
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    }
                } else {
                    Text(
                        text = "Guest",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}