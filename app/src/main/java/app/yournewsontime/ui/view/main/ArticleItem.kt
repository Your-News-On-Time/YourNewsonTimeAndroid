package app.yournewsontime.ui.view.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.yournewsontime.data.model.Article
import coil3.compose.AsyncImage
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticleItem(
    article: Article,
    navController: androidx.navigation.NavController,
) {
    val imageUrl =
        article.multimedia.firstOrNull()?.url?.let { "https://static01.nyt.com/$it" }
    val formatedDate = article.pub_date.split("T").firstOrNull()?.let {
        DateTimeFormatter.ofPattern("dd/MM/yyyy").format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(it)
        )
    } ?: "Unknown"

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
        //TODO: Agregar elevación a la tarjeta
        // elevation = CardElevation(8.dp)
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
                    text = formatedDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray
                )
                //TODO: EStá es la información de las noticias para poder leerlas
                /*Text(
                text = article.snippet ?: "Newspaper",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White
            )*/

                if (imageUrl != null) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = article.headline.main,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }
        }
    }
}