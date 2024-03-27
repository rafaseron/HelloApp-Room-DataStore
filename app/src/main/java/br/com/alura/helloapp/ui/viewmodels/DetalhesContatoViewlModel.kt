package br.com.alura.helloapp.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.room.entities.Contato
import br.com.alura.helloapp.room.repository.ContatoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


data class DetalhesContatoUiState(
    val id: Long = 0L,
    val nome: String = "",
    val sobrenome: String = "",
    val telefone: String = "",
    val fotoPerfil: String = "",
    val aniversario: Date? = null,
)

@HiltViewModel
class DetalhesContatoViewlModel @Inject constructor (private val contatoRepository: ContatoRepository, savedStateHandle: SavedStateHandle) : ViewModel() {

    //private val idContato = savedStateHandle.get<Long>(ID_CONTATO)

    private val _uiState = MutableStateFlow(DetalhesContatoUiState())
    val uiState = _uiState.asStateFlow()
    private fun atualizarContatoDoUiState(contato: Contato) {
        _uiState.value = _uiState.value.copy(nome = contato.nome, sobrenome = contato.sobrenome,
            telefone = contato.telefone, fotoPerfil = contato.fotoPerfil, aniversario = contato.aniversario)
    }

    fun receberIdPeloNavigation(id: Long){
        _uiState.value = _uiState.value.copy(id = id)

        viewModelScope.launch {
            val contatoFiltrado = contatoRepository.searchContactFromId(id)
            contatoFiltrado?.let {
                atualizarContatoDoUiState(it)
            }
        }

    }

    fun deleteContact(){
        viewModelScope.launch {
            contatoRepository.deleteOnDatabase(uiState.value.id)
        }
    }

    suspend fun removeContato() {
    }
}
