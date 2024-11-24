package app.yournewsontime.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.yournewsontime.ui.theme.Branding_YourNewsOnTime
import coil3.compose.AsyncImage

@Composable
fun NewsCard(
    newspaper: String,
    title: String,
    description: String,
    imageUrl: String
) { // TODO change to pass News object

    AsyncImage(
        model = imageUrl,
        contentDescription = title,
    )

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(Branding_YourNewsOnTime)
            .padding(16.dp)
    ) {
        Column {
            Text(newspaper)
            Text(title)
            Text(description)
        }
        /*Image(
            paiter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(imageUrl)
                    .build(),
            ),
            contentDescription = title,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(8.dp)))
        )*/
    }
}

@Composable
fun CardTest() {
    Column(
        modifier = Modifier
            .padding(top = 80.dp)
    ) {
        NewsCard(
            newspaper = "Newspaper",
            title = "Title",
            description = "Description",
            imageUrl = "https://randomuser.me/api/portraits/med/men/64.jpg"
        )
        NewsCard(
            newspaper = "Newspaper",
            title = "Title",
            description = "Description",
            imageUrl = "https://randomuser.me/api/portraits/med/men/64.jpg"
        )
        NewsCard(
            newspaper = "Newspaper",
            title = "Title",
            description = "Description",
            imageUrl = "https://randomuser.me/api/portraits/med/men/64.jpg"
        )
        NewsCard(
            newspaper = "Newspaper",
            title = "Title",
            description = "Description",
            imageUrl = "https://randomuser.me/api/portraits/med/men/64.jpg"
        )
        NewsCard(
            newspaper = "Newspaper",
            title = "Title",
            description = "Description",
            imageUrl = "https://randomuser.me/api/portraits/med/men/64.jpg"
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun NewsCardPreview() {
    // NewsCard(news = News("Title", "Description", "Image URL"))
    CardTest()
}