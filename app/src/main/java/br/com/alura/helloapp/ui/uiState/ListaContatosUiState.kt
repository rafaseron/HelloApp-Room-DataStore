package br.com.alura.helloapp.ui.uiState

import br.com.alura.helloapp.room.entities.Contato

data class ListaContatosUiState(
    val contatos: List<Contato> = emptyList(),
    val logado: Boolean = true
)