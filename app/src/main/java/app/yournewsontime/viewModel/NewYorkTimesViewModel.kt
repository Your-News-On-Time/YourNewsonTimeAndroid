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