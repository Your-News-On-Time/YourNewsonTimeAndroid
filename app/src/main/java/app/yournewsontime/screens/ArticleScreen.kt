package app.yournewsontime.screens

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import app.yournewsontime.viewModel.NewYorkTimesViewModel
import coil3.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import app.yournewsontime.data.model.getMultimediaList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    navController: NavController,
    authRepository: FirebaseAuthRepository,
    articleId: String,
    viewModel: NewYorkTimesViewModel
) {
    val articleData = viewModel.getArticleById(Uri.decode(articleId))

    if (articleData == null) {
        Text("Artículo no encontrado")
        return
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val gson = remember { Gson() }

    val imageUrl = articleData.getMultimediaList(gson)
        .firstOrNull { it.type == "image" && !it.url.isNullOrBlank() && it.url.endsWith(".jpg") }
        ?.url
        ?.let { url ->
            if (url.startsWith("http")) url else "https://static01.nyt.com/$url"
        }

    var dateNow by remember {
        mutableStateOf(
            LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        )
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var isMenuOpen by remember { mutableStateOf(false) }

    LaunchedEffect(drawerState.isOpen) {
        isMenuOpen = drawerState.isOpen
    }

    LaunchedEffect(Unit) {
        while (true) {
            val newDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            if (dateNow != newDate) {
                dateNow = newDate
            }
            delay(60 * 1000L)
        }
    }

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
                                contentDescription = "Back"
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
                            if (drawerState.isClosed) {
                                drawerState.open()
                            } else {
                                drawerState.close()
                            }
                        }
                    },
                    isMenuOpen = isMenuOpen
                )
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(Color.White)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    imageUrl?.let {
                        Image(
                            painter = rememberAsyncImagePainter(it),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(380.dp)
                                .padding(top = 65.dp)
                                .clip(RoundedCornerShape(16.dp))
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    Text(
                        text = articleData.headline?.main ?: "Sin título",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = articleData.snippet ?: "Sin resumen disponible",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = articleData.lead_paragraph ?: "Contenido no disponible",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(articleData.web_url))
                            context.startActivity(intent)
                        }
                    ) {
                        Text("Open article")
                    }
                }
            }
        )
    }
}
