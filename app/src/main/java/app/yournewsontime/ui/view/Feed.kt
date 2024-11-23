package app.yournewsontime.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Feed() {
    Column {
        // TODO Header

        Column {
            // TODO charge all the news from the API
            Text("Feed with news")
        }

        // TODO Footer
    }
}

@Preview(showSystemUi = true)
@Composable
fun FeedPreview() {
    Feed()
}