package br.com.alura.helloapp.ui.viewmodels

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import br.com.alura.helloapp.R
import br.com.alura.helloapp.extensions.converteParaDate
import br.com.alura.helloapp.extensions.converteParaString
import br.com.alura.helloapp.room.entities.Contato
import br.com.alura.helloapp.room.repository.ContatoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date
import javax.inject.Inject


data class FormularioContatoUiState(
    val id: Long = 0L,
    val nome: String = "",
    val sobrenome: String = "",
    val telefone: String = "",
    val fotoPerfil: String = "",
    val aniversario: Date? = null,
    val mostrarCaixaDialogoImagem: Boolean = false,
    val mostrarCaixaDialogoData: Boolean = false,
    val onNomeMudou: (String) -> Unit = {},
    val onSobrenomeMudou: (String) -> Unit = {},
    val onTelefoneMudou: (String) -> Unit = {},
    val onFotoPerfilMudou: (String) -> Unit = {},
    val onAniversarioMudou: (String) -> Unit = {},
    val onMostrarCaixaDialogoImagem: (mostrar: Boolean) -> Unit = {},
    val onMostrarCaixaDialogoData: (mostrar: Boolean) -> Unit = {},
    val textoAniversairo: String = "",
    @StringRes val tituloAppbar: Int? = R.string.titulo_cadastro_contato,
)

@HiltViewModel
class FormularioContatoViewModel @Inject constructor(private val contatoRepository: ContatoRepository, savedStateHandle: SavedStateHandle) : ViewModel() {

    //private val idContato = savedStateHandle.get<Long>(ID_CONTATO)

    private val _uiState = MutableStateFlow(FormularioContatoUiState())
    val uiState: StateFlow<FormularioContatoUiState>
        get() = _uiState.asStateFlow()


    init {

        _uiState.update { state ->
            state.copy(onNomeMudou = {
                _uiState.value = _uiState.value.copy(
                    nome = it
                )
            }, onSobrenomeMudou = {
                _uiState.value = _uiState.value.copy(
                    sobrenome = it
                )
            }, onTelefoneMudou = {
                _uiState.value = _uiState.value.copy(
                    telefone = it
                )
            }, onFotoPerfilMudou = {
                _uiState.value = _uiState.value.copy(
                    fotoPerfil = it
                )
            }, onAniversarioMudou = {
                _uiState.value = _uiState.value.copy(
                    aniversario = it.converteParaDate(), mostrarCaixaDialogoData = false
                )
            }, onMostrarCaixaDialogoImagem = {
                _uiState.value = _uiState.value.copy(
                    mostrarCaixaDialogoImagem = it
                )
            }, onMostrarCaixaDialogoData = {
                _uiState.value = _uiState.value.copy(
                    mostrarCaixaDialogoData = it
                )
            })
        }
    }

    fun defineTextoAniversario(textoAniversario: String) {
        val textoAniversairo = _uiState.value.aniversario?.converteParaString() ?: textoAniversario

        _uiState.update {
            it.copy(textoAniversairo = textoAniversairo)
        }
    }

    fun carregaImagem(url: String) {
        _uiState.value = _uiState.value.copy(
            fotoPerfil = url, mostrarCaixaDialogoImagem = false
        )
    }

    suspend fun onSaveClick(){
        val contato = Contato(nome = uiState.value.nome, sobrenome = uiState.value.sobrenome, fotoPerfil = uiState.value.fotoPerfil,
            telefone = uiState.value.telefone, aniversario = uiState.value.aniversario)
        contatoRepository.insertOnDatabase(contato)

        ListaContatosViewModel(contatoRepository).updateContactList()
    }
}
