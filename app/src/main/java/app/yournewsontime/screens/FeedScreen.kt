package app.yournewsontime.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.yournewsontime.data.repository.CategoryProvider
import app.yournewsontime.data.repository.FirebaseAuthRepository
import app.yournewsontime.navigation.AppScreens
import app.yournewsontime.ui.components.AlertDialog
import app.yournewsontime.ui.components.ArticleCard
import app.yournewsontime.ui.components.DrawerContent
import app.yournewsontime.ui.components.Footer
import app.yournewsontime.viewModel.NewYorkTimesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FeedScreen(
    navController: NavController,
    authRepository: FirebaseAuthRepository,
    viewModel: NewYorkTimesViewModel,
    apiKey: String
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var dateNow by remember {
        mutableStateOf(
            LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        )
    }
    var isMenuOpen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            val newDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            if (dateNow != newDate) {
                dateNow = newDate
            }
            delay(60 * 1000L)
        }
    }

    LaunchedEffect(drawerState.isOpen) {
        isMenuOpen = drawerState.isOpen
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navController = navController,
                authRepository = authRepository,
                viewModel = viewModel,
                apiKey = apiKey,
                beginDate = LocalDate.now().minusDays(1)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                endDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            )
        },
        scrimColor = Color.Transparent
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "$dateNow",
                            color = Color.Gray.copy(alpha = 0.9f)
                        )
                    },
                    colors = TopAppBarColors(
                        titleContentColor = Color.Black,
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        navigationIconContentColor = Color.Transparent,
                        actionIconContentColor = Color.Transparent
                    ),
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
                            drawerState.open()
                        }
                    },
                    isMenuOpen = isMenuOpen
                )
            }
        ) { padding ->
            FeedBodyContent(
                navController,
                authRepository,
                viewModel,
                apiKey,
                padding
            )
        }
    }

}

@SuppressLint("NewApi")
@Composable
fun FeedBodyContent(
    navController: NavController,
    authRepository: FirebaseAuthRepository,
    viewModel: NewYorkTimesViewModel,
    apiKey: String,
    padding: PaddingValues
) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val beginDate = LocalDate.now().minusDays(1).format(formatter)
    val endDate = LocalDate.now().format(formatter)
    val followedCategoriesOnString = CategoryProvider.getFollowedCategoriesOnString()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val articles by viewModel.articles
    val error by viewModel.errorMessage
    val isLoading by viewModel.isLoading

    LaunchedEffect(Unit) {
        viewModel.fetchArticles(followedCategoriesOnString, apiKey, beginDate, endDate)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                if (error != null) {
                    Text("Error: $error", color = MaterialTheme.colorScheme.error)
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 10.dp)
                    ) {
                        items(articles) { article ->

                            ArticleCard(
                                article = article,
                                onClick = {
                                    println(article._id.split("/").last())
                                    navController.navigate(
                                        AppScreens.ArticleScreen.route + "/${
                                            article._id.split(
                                                "/"
                                            ).last()
                                        }"
                                    )
                                }
                            )

                        }
                    }
                }
            }

            errorMessage?.let {
                AlertDialog(message = it)
                errorMessage = null
            }
        }
    }
}

