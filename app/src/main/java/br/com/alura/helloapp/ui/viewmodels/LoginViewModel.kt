package br.com.alura.helloapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class LoginUiState(
    val usuario: String = "",
    val senha: String = "",
    val exibirErro: Boolean = false,
    val onErro: (Boolean) -> Unit = {},
    val onUsuarioMudou: (String) -> Unit = {},
    val onSenhaMudou: (String) -> Unit = {},
    val onClickLogar: () -> Unit = {},
    val logado: Boolean = false
)

class LoginViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState>
        get() = _uiState.asStateFlow()

    init {
        _uiState.update { state ->
            state.copy(
                onUsuarioMudou = {
                    _uiState.value = _uiState.value.copy(
                        usuario = it
                    )
                },
                onSenhaMudou = {
                    _uiState.value = _uiState.value.copy(
                        senha = it
                    )
                },
                onErro = {
                    _uiState.value = _uiState.value.copy(
                        exibirErro = it
                    )
                },
            )
        }
    }

    fun tentaLogar() {
        logaUsuario()
    }

    private fun logaUsuario() {
        _uiState.value = _uiState.value.copy(
            logado = true
        )
    }
}


