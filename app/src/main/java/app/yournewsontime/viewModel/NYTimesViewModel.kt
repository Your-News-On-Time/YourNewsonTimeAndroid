package app.yournewsontime.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.yournewsontime.data.repository.NYTimesRepository
import app.yournewsontime.data.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NYTimesViewModel(private val repository: NYTimesRepository) : ViewModel() {
    val articles = mutableStateOf<List<Article>>(emptyList())
    val errorMessage = mutableStateOf<String?>(null)

    fun fetchArticles(query: String, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchArticles(query, apiKey) { result, error ->
                if (error != null) {
                    errorMessage.value = error
                } else {
                    articles.value = result ?: emptyList()
                }
            }
        }
    }
}