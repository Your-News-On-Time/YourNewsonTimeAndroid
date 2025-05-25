package app.yournewsontime.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.yournewsontime.data.model.Article
import app.yournewsontime.data.repository.NewYorkTimesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewYorkTimesViewModel(private val repository: NewYorkTimesRepository) : ViewModel() {
    val articles = mutableStateOf<List<Article>>(emptyList())
    val errorMessage = mutableStateOf<String?>(null)
    val isLoading = mutableStateOf(false)

    fun fetchArticles(
        category: String,
        apiKey: String,
        beginDate: String? = null,
        endDate: String? = null
    ) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            if (beginDate != null && endDate != null) {
                repository.searchArticles(category, apiKey, beginDate, endDate) { result, error ->
                    if (error != null) {
                        errorMessage.value = error
                    } else {
                        articles.value = result?.sortedByDescending { it.pub_date ?: "" } ?: emptyList()
                    }
                    isLoading.value = false
                }
            }
        }
    }

    fun getArticleById(articleId: String): Article? {
        return try {
            articles.value.firstOrNull { it._id == articleId }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
