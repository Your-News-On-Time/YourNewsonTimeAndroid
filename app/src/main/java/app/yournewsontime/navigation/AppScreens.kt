package app.yournewsontime.navigation

sealed class AppScreens(val route: String) {
    object StartPageScreen : AppScreens("start_page_screen")
    object RegisterScreen : AppScreens("register_screen")
    object LoginScreen : AppScreens("login_screen")
    object FeedScreen : AppScreens("feed_screen")
}