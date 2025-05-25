package app.yournewsontime.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import app.yournewsontime.data.model.getMultimediaList
import coil3.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticleCard(article: Article, onClick: () -> Unit) {
    val gson = Gson()
    val multimediaList = article.getMultimediaList(gson)

    multimediaList.forEachIndexed { index, item ->
        println("🔍 Multimedia[$index] → url = ${item.url}, type = ${item.type}")
    }

    val imageUrl = multimediaList
        .firstOrNull { !it.url.isNullOrBlank() && it.url!!.endsWith(".jpg") }
        ?.url
        ?.let { "https://static01.nyt.com/$it" }

    println("🖼️ Mostrando imagen: $imageUrl")

    val date = article.pub_date?.split("T")?.firstOrNull()?.let {
        DateTimeFormatter.ofPattern("dd/MM/yyyy").format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(it)
        )
    } ?: "Unknown"

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
                    text = article.headline?.main ?: "Sin título",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray
                )
            }

            imageUrl?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }
}
