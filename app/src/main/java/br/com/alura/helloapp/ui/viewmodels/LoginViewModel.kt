package br.com.alura.helloapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.localData.room.repository.UsernameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


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

@HiltViewModel
class LoginViewModel @Inject constructor(val usernameRepository: UsernameRepository): ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

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
        val username = uiState.value.usuario
        val senha = uiState.value.senha

        viewModelScope.launch {
            val response = usernameRepository.verificarUsuario(username = username, password = senha)
            if (response){
                logaUsuario()
            } else{ _uiState.value = _uiState.value.copy(exibirErro = true) }
        }
    }

    private fun logaUsuario() {
        _uiState.value = _uiState.value.copy(
            logado = true
        )
    }
}


