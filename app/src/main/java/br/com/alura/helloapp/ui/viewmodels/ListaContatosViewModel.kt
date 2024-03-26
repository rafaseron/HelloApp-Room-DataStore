package br.com.alura.helloapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.helloapp.room.entities.Contato
import br.com.alura.helloapp.room.repository.ContatoRepository
import br.com.alura.helloapp.ui.uiState.ListaContatosUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaContatosViewModel @Inject constructor(private val contatoRepository: ContatoRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ListaContatosUiState())
    val uiState: StateFlow<ListaContatosUiState>
        get() = _uiState.asStateFlow()

    private fun updateContactList(list: List<Contato>){
        _uiState.value = _uiState.value.copy(contatos = list)
    }

    init {
        viewModelScope.launch {
            updateContactList(contatoRepository.getAllContacts())
        }
    }

}