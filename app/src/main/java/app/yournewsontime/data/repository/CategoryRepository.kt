package app.yournewsontime.data.repository

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class Category(val name: String) {
    var isFollowed: Boolean = false
}

object CategoryProvider {
    private val allCategories = listOf(
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

    val followedCategories: SnapshotStateList<Category> = mutableStateListOf()
    val suggestedCategories: SnapshotStateList<Category> = mutableStateListOf()

    fun initializeCategories(context: Context) {
        val prefs = context.getSharedPreferences("categories_prefs", Context.MODE_PRIVATE)
        val followedNames = prefs.getStringSet("followed_categories", emptySet()) ?: emptySet()

        followedCategories.clear()
        suggestedCategories.clear()

        allCategories.forEach { category ->
            if (category.name in followedNames) {
                category.isFollowed = true
                followedCategories.add(category)
            } else {
                category.isFollowed = false
                suggestedCategories.add(category)
            }
        }
    }

    fun saveCategories(context: Context) {
        val prefs = context.getSharedPreferences("categories_prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val followedNames = followedCategories.map { it.name }.toSet()
        editor.putStringSet("followed_categories", followedNames)

        editor.apply()
    }

    fun followCategory(category: Category) {
        category.isFollowed = true
        followedCategories.add(category)
        suggestedCategories.remove(category)
    }

    fun unfollowCategory(category: Category) {
        category.isFollowed = false
        followedCategories.remove(category)
        suggestedCategories.add(category)
    }
}