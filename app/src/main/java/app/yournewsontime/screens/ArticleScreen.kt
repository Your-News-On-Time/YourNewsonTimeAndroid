package app.yournewsontime.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
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
import app.yournewsontime.ui.view.main.ArticleItem
import app.yournewsontime.viewmodel.NewYorkTimesViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(viewModel: NewYorkTimesViewModel, apiKey: String) {
    val articles by viewModel.articles
    val error by viewModel.errorMessage

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val beginDate = LocalDate.now().minusDays(1).format(formatter)
    val endDate = LocalDate.now().format(formatter)
    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))


    // Fetch articles when the composable is launched
    LaunchedEffect(Unit) {
        viewModel.fetchArticles("War", apiKey, beginDate, endDate)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("$currentDate") })
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
