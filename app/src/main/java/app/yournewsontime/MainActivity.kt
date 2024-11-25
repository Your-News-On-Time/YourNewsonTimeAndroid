package app.yournewsontime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import app.yournewsontime.data.repository.NYTimesRepository
import app.yournewsontime.data.repository.api.ApiClient
import app.yournewsontime.data.repository.api.NYTimesApiService
import app.yournewsontime.viewModel.NYTimesViewModel
import app.yournewsontime.viewModel.NYTimesViewModelFactory
import app.yournewsontime.ui.navigation.NavGraph
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiKey = getString(R.string.apiKey)
        val auth = FirebaseAuth.getInstance()
        val startDestination = if (auth.currentUser != null) "feed" else "startPage"

        setContent {
            val navController = rememberNavController()
            val repository = NYTimesRepository(ApiClient.retrofit.create(NYTimesApiService::class.java))
            val viewModel: NYTimesViewModel = viewModel(factory = NYTimesViewModelFactory(repository))

            NavGraph(
                navController = navController,
                startDestination = startDestination,
                viewModel = viewModel,
                apiKey = apiKey
            )
        }
    }
}