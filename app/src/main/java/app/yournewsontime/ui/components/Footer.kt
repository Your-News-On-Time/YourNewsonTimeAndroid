package app.yournewsontime.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.yournewsontime.R
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.navigation.AppScreens
import app.yournewsontime.ui.theme.Branding_YourNewsOnTime_Background
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Footer(
    navController: NavController,
    authRepository: FirebaseAuthRepository,
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit,
    isMenuOpen: Boolean
) {
    val scope = rememberCoroutineScope()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Branding_YourNewsOnTime_Background),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(
                id = if (isMenuOpen) R.drawable.close_menu_icon else R.drawable.menu_icon
            ),
            contentDescription = if (isMenuOpen) "Close Menu" else "Menu",
            modifier = Modifier
                .clickable {
                    onMenuClick()
                }
                .size(24.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.today_icon),
            contentDescription = "Today",
            modifier = Modifier
                .clickable {
                    scope.launch {
                        if (navController.currentDestination?.route == "feed_screen") {
                            // TODO: Implement logic to refresh the feed
                        } else {
                            navController.navigate(AppScreens.FeedScreen.route) {
                                popUpTo(AppScreens.FeedScreen.route) { inclusive = true }
                            }
                        }
                    }
                }
                .size(24.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.bookmark_icon),
            contentDescription = "Saved",
            modifier = Modifier
                .clickable {
                    scope.launch {
                        navController.navigate(AppScreens.SavedScreen.route)
                    }
                }
                .size(24.dp)
        )

        if (
            authRepository.isUserLoggedIn() &&
            !authRepository.isUserAnonymous() &&
            authRepository.getCurrentUser()?.photoUrl != null
        ) {
            AsyncImage(
                model = authRepository.getCurrentUser()?.photoUrl,
                contentDescription = authRepository.getCurrentUser()?.displayName,
                modifier = Modifier
                    .clickable {
                        scope.launch {
                            navController.navigate(AppScreens.ProfileScreen.route)
                        }
                    }
                    .clip(CircleShape)
                    .background(color = Color.Gray)
                    .size(30.dp)
            )
        } else {
            Image(
                painter = painterResource(
                    id = R.drawable.account_icon
                ),
                contentDescription = "Profile",
                modifier = Modifier
                    .clickable {
                        scope.launch {
                            navController.navigate(AppScreens.ProfileScreen.route)
                        }
                    }
                    .size(24.dp)
            )
        }
    }
}