package app.yournewsontime.data.repository

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import app.yournewsontime.viewModel.NewYorkTimesViewModel

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

    fun followCategory(
        category: Category,
        viewModel: NewYorkTimesViewModel? = null,
        apiKey: String? = null,
        beginDate: String? = null,
        endDate: String? = null
    ) {
        category.isFollowed = true
        followedCategories.add(category)
        suggestedCategories.remove(category)
        followedCategories.sortBy { it.name }
        suggestedCategories.sortBy { it.name }
        if (viewModel != null && apiKey != null && beginDate != null && endDate != null) {
            viewModel.fetchArticles(getFollowedCategoriesOnString(), apiKey, beginDate, endDate)
        }
    }

    fun unfollowCategory(
        category: Category,
        viewModel: NewYorkTimesViewModel? = null,
        apiKey: String? = null,
        beginDate: String? = null,
        endDate: String? = null
    ) {
        category.isFollowed = false
        followedCategories.remove(category)
        suggestedCategories.add(category)
        followedCategories.sortBy { it.name }
        suggestedCategories.sortBy { it.name }
        if (viewModel != null && apiKey != null && beginDate != null && endDate != null) {
            viewModel.fetchArticles(getFollowedCategoriesOnString(), apiKey, beginDate, endDate)
        }
    }

    fun clearCategories() {
        followedCategories.clear()
        suggestedCategories.clear()
    }

    fun getFollowedCategoriesOnString(): String {
        if (followedCategories.size > 2) {
            return followedCategories.shuffled().take(2).joinToString(",") { it.name }
        }
        return followedCategories.joinToString(",") { it.name }
    }

    fun getFollowedCategoriesOnList(): List<String> {
        return followedCategories.map { it.name }
    }
}