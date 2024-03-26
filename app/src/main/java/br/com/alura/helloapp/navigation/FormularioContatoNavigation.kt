package br.com.alura.helloapp.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.helloapp.R
import br.com.alura.helloapp.room.entities.Contato
import br.com.alura.helloapp.room.repository.ContatoRepository
import br.com.alura.helloapp.ui.screens.FormularioContatoTela
import br.com.alura.helloapp.ui.viewmodels.FormularioContatoViewModel
import br.com.alura.helloapp.util.ID_CONTATO
import kotlinx.coroutines.launch

fun NavGraphBuilder.formularioContatoGraph(
    navController: NavHostController
) {
    composable(
        route = FormularioContato.rotaComArgumentos,
        arguments = FormularioContato.argumentos
    ) { navBackStackEntry ->
        navBackStackEntry.arguments?.getLong(
            ID_CONTATO
        )?.let { idContato ->

            val viewModel = hiltViewModel<FormularioContatoViewModel>()
            val state by viewModel.uiState.collectAsState()
            val context = LocalContext.current
            val contatoRepository = ContatoRepository(context)

            LaunchedEffect(state.aniversario) {
                viewModel.defineTextoAniversario(
                    context.getString(R.string.aniversario)
                )
            }

            val scope = rememberCoroutineScope()

            FormularioContatoTela(
                state = state,
                onClickSalvar = {
                    scope.launch {
                        contatoRepository.insertOnDatabase(Contato(nome = state.nome, sobrenome = state.sobrenome, telefone = state.telefone, fotoPerfil = state.fotoPerfil, aniversario = state.aniversario))
                    }
                    navController.popBackStack()
                },
                onCarregarImagem = {
                    viewModel.carregaImagem(it)
                }
            )
        }
    }
}