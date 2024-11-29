package app.yournewsontime.navigation

sealed class AppScreens(val route: String) {
    object StartScreen : AppScreens("start_screen")
    object RegisterScreen : AppScreens("register_screen")
    object LoginScreen : AppScreens("login_screen")
    object FeedScreen : AppScreens("feed_screen")
    object ArticleScreen : AppScreens("article_screen")
    object ProfileScreen : AppScreens("profile_screen")
    object SavedScreen : AppScreens("saved_screen")
}