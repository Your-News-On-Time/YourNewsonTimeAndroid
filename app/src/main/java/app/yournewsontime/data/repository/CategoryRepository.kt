package app.yournewsontime.data.repository

data class Category(
    val name: String,
    var isFollowed: Boolean = false
)

object CategoryProvider {
    val categories = listOf(
        Category("Arts"),
        Category("Automobiles"),
        Category("Blogs"),
        Category("Books"),
        Category("Business"),
        Category("Crosswords & Games"),
        Category("Dining & Wine"),
        Category("Education"),
        Category("Fashion & Style"),
        Category("Food"),
        Category("Health"),
        Category("Home & Garden"),
        Category("Learning"),
        Category("Magazine"),
        Category("Movies"),
        Category("Multimedia"),
        Category("Olympics"),
        Category("Science"),
        Category("Sports"),
        Category("Style"),
        Category("Technology"),
        Category("Theater"),
        Category("Travel")
    )
}