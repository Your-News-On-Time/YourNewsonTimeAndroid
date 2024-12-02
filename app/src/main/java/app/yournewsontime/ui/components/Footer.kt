package app.yournewsontime.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.yournewsontime.R
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.navigation.AppScreens
import app.yournewsontime.ui.theme.Branding_YourNewsOnTime_Background
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

        Image(
            painter = painterResource(
                id = if (authRepository.isUserLoggedIn()) {
                    R.drawable.account_icon
                    // TODO: Placeholder if user does not have a Google profile picture
                } else {
                    R.drawable.account_icon
                }
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