package app.yournewsontime.viewmodel

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

    fun fetchArticles(
        category: String,
        apiKey: String,
        beginDate: String? = null,
        endDate: String? = null
    ) {
<<<<<<< HEAD

=======
>>>>>>> 5737b26ae114ec3645233d11927246318341c102
        viewModelScope.launch(Dispatchers.IO) {
            if (beginDate != null && endDate != null) {
                repository.searchArticles(category, apiKey, beginDate, endDate) { result, error ->
                    if (error != null) {
                        errorMessage.value = error
                    } else {
                        articles.value = result ?: emptyList()
                    }
                }
            }
        }
    }

    fun getArticleById(articleId: String): Article {
        return articles.value.first { it._id == articleId }
    }
}