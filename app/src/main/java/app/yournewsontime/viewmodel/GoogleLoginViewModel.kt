package app.yournewsontime.viewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.yournewsontime.data.repository.FirebaseAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GoogleLoginViewModel(private val authRepository: FirebaseAuthRepository) : ViewModel() {

    private val _googleLoginState = MutableStateFlow<GoogleLoginState>(GoogleLoginState.Idle)
    val googleLoginState: StateFlow<GoogleLoginState> = _googleLoginState

    fun loginWithGoogle(data: Intent?) {
        viewModelScope.launch {
            _googleLoginState.value = GoogleLoginState.Loading
            val result = authRepository.loginWithGoogle(data)
            if (result.isSuccess) {
                _googleLoginState.value = GoogleLoginState.Success
            } else {
                _googleLoginState.value =
                    GoogleLoginState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    fun getGoogleSignInIntent(context: Context): Intent {
        return authRepository.getGoogleSignInIntent()
    }

    fun resetState() {
        _googleLoginState.value = GoogleLoginState.Idle
    }
}

sealed class GoogleLoginState {
    object Idle : GoogleLoginState()
    object Loading : GoogleLoginState()
    object Success : GoogleLoginState()
    data class Error(val message: String) : GoogleLoginState()
}