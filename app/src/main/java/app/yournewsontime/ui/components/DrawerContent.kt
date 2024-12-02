package app.yournewsontime.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.yournewsontime.R
import app.yournewsontime.data.repository.CategoryProvider
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.navigation.AppScreens
import app.yournewsontime.ui.theme.Branding_YourNewsOnTime
import app.yournewsontime.ui.theme.interFontFamily

@Composable
fun DrawerContent(
    navController: NavController,
    authRepository: FirebaseAuthRepository,
    footerHeight: Dp = 56.dp,
) {
    val currentUser = authRepository.getCurrentUser()
    val userNickname = if (currentUser?.isAnonymous == false) {
        currentUser.email?.split("@")?.get(0) ?: "Unknown"
    } else {
        "Guest"
    }
    val categories = CategoryProvider.categories
    val followingCategories = remember { categories.filter { it.isFollowed } }
    val suggestedCategories = remember { categories.filter { !it.isFollowed } }

    Box(
        modifier = Modifier
            .padding(bottom = footerHeight)
            .fillMaxHeight()
            .background(Color.White)
            .width(300.dp)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val startX = size.width - strokeWidth / 2
                val startY = 0f
                val endX = size.width - strokeWidth / 2
                val endY = size.height
                drawLine(
                    color = Color.LightGray.copy(alpha = 0.4f),
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = strokeWidth
                )
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = "Hello, $userNickname!",
                    fontSize = 18.sp,
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Branding_YourNewsOnTime,
                )

                Spacer(modifier = Modifier.height(16.dp))

                DrawerItem(
                    text = "Feed",
                    icon = {
                        Image(
                            painter = painterResource(
                                id = if (navController.currentDestination?.route == "feed_screen") {
                                    R.drawable.today_icon
                                } else {
                                    R.drawable.calendar_icon
                                }
                            ),
                            contentDescription = "Today",
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    onClick = {
                        navController.navigate(AppScreens.FeedScreen.route) {
                            popUpTo(AppScreens.FeedScreen.route) { inclusive = true }
                        }
                    }
                )

                DrawerItem(
                    text = "Saved Articles",
                    icon = {
                        Image(
                            painter = painterResource(
                                id = if (navController.currentDestination?.route == AppScreens.SavedScreen.route) {
                                    R.drawable.filled_bookmark_icon
                                } else {
                                    R.drawable.bookmark_icon
                                }
                            ),
                            contentDescription = "Saved",
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    onClick = {
                        navController.navigate(AppScreens.SavedScreen.route)
                    }
                )

                Splitter()
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Following",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    followingCategories.forEach { category ->
                        Text(
                            text = category.name,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.clickable {
                                // TODO: Implement click logic for followed categories
                                category.isFollowed = false
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Suggested",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    suggestedCategories.forEach { category ->
                        Text(
                            text = category.name,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.clickable {
                                // TODO: Implement logic to follow this category
                                category.isFollowed = true
                            }
                        )
                    }
                }
            }

            if (!authRepository.isUserAnonymous()) {
                Text(
                    text = "Log out",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            authRepository.logout()
                            navController.navigate(AppScreens.LoginScreen.route) {
                                popUpTo(AppScreens.StartScreen.route) { inclusive = true }
                            }
                        }
                        .padding(vertical = 16.dp),
                    fontSize = 14.sp,
                    color = Color.Red.copy(alpha = 0.8f),
                    textAlign = TextAlign.Start,
                )
            } else {
                Text(
                    text = "Sign in",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(AppScreens.LoginScreen.route) {
                                popUpTo(AppScreens.StartScreen.route) { inclusive = true }
                            }
                        }
                        .padding(vertical = 16.dp),
                    fontSize = 14.sp,
                    color = Branding_YourNewsOnTime.copy(alpha = 0.8f),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Composable
fun DrawerItem(
    text: String,
    icon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.invoke()
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = 8.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}