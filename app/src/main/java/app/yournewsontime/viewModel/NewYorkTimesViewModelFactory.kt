package app.yournewsontime.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.yournewsontime.data.repository.NewYorkTimesRepository

@Suppress("UNCHECKED_CAST")
class NewYorkTimesViewModelFactory(private val repository: NewYorkTimesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewYorkTimesViewModel::class.java)) {
            return NewYorkTimesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}