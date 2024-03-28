package br.com.alura.helloapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.localData.room.entities.Usuario
import br.com.alura.helloapp.localData.room.repository.UsernameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FormularioLoginUiState(
    val nome: String = "",
    val usuario: String = "",
    val senha: String = "",
    val onNomeMudou: (String) -> Unit = {},
    val onUsuarioMudou: (String) -> Unit = {},
    val onSenhaMudou: (String) -> Unit = {},
    val onClickSalvar: () -> Unit = {}
)

@HiltViewModel
class FormularioLoginViewModel @Inject constructor(val usernameRepository: UsernameRepository): ViewModel() {

    private val _uiState = MutableStateFlow(FormularioLoginUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { state ->
            state.copy(
                onNomeMudou = {
                    _uiState.value = _uiState.value.copy(
                        nome = it
                    )
                },
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
            )
        }
    }

    fun criarUsuario(){
        val usuario = Usuario(nome = uiState.value.nome, usuario = uiState.value.usuario, senha = uiState.value.senha)
        if (uiState.value.usuario !== "" && uiState.value.senha !== "" && uiState.value.nome !== ""){
            viewModelScope.launch{
                usernameRepository.createNewUser(usuario)

                //para verificar no console se deu certo
                val pesquisa = usernameRepository.searchUserByUsername(uiState.value.usuario)
                pesquisa.let {
                    Log.e("FormularioLogin", "$it")
                }

            }
        }

    }

}
