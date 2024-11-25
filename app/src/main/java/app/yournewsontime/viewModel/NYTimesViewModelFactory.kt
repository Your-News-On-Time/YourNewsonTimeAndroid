package app.yournewsontime.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.yournewsontime.data.repository.NYTimesRepository

@Suppress("UNCHECKED_CAST")
class NYTimesViewModelFactory(private val repository: NYTimesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NYTimesViewModel::class.java)) {
            return NYTimesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}