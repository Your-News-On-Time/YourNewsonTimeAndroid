package app.yournewsontime.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.ui.components.DrawerContent
import app.yournewsontime.ui.components.Footer
import app.yournewsontime.viewmodel.NewYorkTimesViewModel
import coil3.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    navController: NavController,
    authRepository: FirebaseAuthRepository,
    articleId: String,
    viewModel: NewYorkTimesViewModel
) {

    val articleData = viewModel.getArticleById("nyt://article/${articleId}")
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val context = LocalContext.current
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController, authRepository)
        },
        scrimColor = Color.Transparent
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Article") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                Footer(
                    navController = navController,
                    authRepository = authRepository,
                    modifier = Modifier.fillMaxWidth(),
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            content = { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    val imageUrl =
                        articleData.multimedia.firstOrNull()?.url?.let { "https://static01.nyt.com$it" }
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
                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    ) {
                        Text(
                            text = articleData.headline.main ?: "Newspaper",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = articleData.snippet ?: "Date & Hour of the article",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = articleData.lead_paragraph ?: "Date & Hour of the article",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Black
                        )
                        Spacer(
                            modifier = Modifier.height(40.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = {
                                val intent =
                                    Intent(Intent.ACTION_VIEW, Uri.parse(articleData.web_url))
                                context.startActivity(intent)
                            }) {
                                Text("Open article")
                            }
                        }
                    }


                }


            }

        )
    }
}