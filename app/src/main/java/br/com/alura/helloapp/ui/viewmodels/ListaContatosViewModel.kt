package br.com.alura.helloapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import br.com.alura.helloapp.room.entities.Contato
import br.com.alura.helloapp.ui.uiState.ListaContatosUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListaContatosViewModel(
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListaContatosUiState())
    val uiState: StateFlow<ListaContatosUiState>
        get() = _uiState.asStateFlow()

    fun getContactList(list: List<Contato>){
        _uiState.value = _uiState.value.copy(contatos = list)
    }

}