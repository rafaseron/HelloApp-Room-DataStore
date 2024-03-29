package br.com.alura.helloapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SplashScreenUiState(val appState: AppState = AppState.Carregando)
sealed class AppState {
    object Carregando : AppState()
    object Logado : AppState()
    object Deslogado : AppState()
}

class SplashScreenViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(SplashScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            definiDestinoInicial()
        }
    }

    private fun definiDestinoInicial() {
    }

    fun atualizarAppState(appState: AppState){
        _uiState.value = _uiState.value.copy(appState = appState)
    }
}