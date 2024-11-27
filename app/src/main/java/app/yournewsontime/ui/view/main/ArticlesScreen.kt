package app.yournewsontime.ui.view.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.yournewsontime.data.model.Article
import app.yournewsontime.viewModel.NYTimesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen(viewModel: NYTimesViewModel, apiKey: String) {
    val articles by viewModel.articles
    val error by viewModel.errorMessage

    // Fetch articles when the composable is launched
    LaunchedEffect(Unit) {
        viewModel.fetchArticles("technology", apiKey)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("NYTimes Articles") })
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (error != null) {
                Text("Error: $error", color = MaterialTheme.colorScheme.error)
            } else {
                LazyColumn {
                    items(articles) { article ->
                        ArticleItem(article)
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleItem(article: Article) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = article.headline.main, style = MaterialTheme.typography.titleMedium)
        Text(text = article.web_url, style = MaterialTheme.typography.bodySmall)
    }
}