package app.yournewsontime.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArticleScreen(navController: NavController) {
    Scaffold {
        ArticleBodyContent(navController)
    }
}


@Composable
fun ArticleBodyContent(navController: NavController) {
    val scope = rememberCoroutineScope()

}