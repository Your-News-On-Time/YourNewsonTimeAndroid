package app.yournewsontime.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.yournewsontime.data.model.Article
import coil3.compose.rememberAsyncImagePainter


@Composable
fun ArticleCard(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium.copy(CornerSize(16.dp)),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(Color(0xFF5E877A))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = article.headline.main,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = article.pub_date ?: "Date & Hour of the article",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray
                )
                val imageUrl =
                    article.multimedia.firstOrNull()?.url?.let { "https://static01.nyt.com$it" }

                if (imageUrl != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                }
            }
        }
    }
}