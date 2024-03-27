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
import br.com.alura.helloapp.ui.screens.FormularioContatoTela
import br.com.alura.helloapp.ui.viewmodels.FormularioContatoViewModel
import br.com.alura.helloapp.util.ID_CONTATO
import kotlinx.coroutines.launch

fun NavGraphBuilder.formularioContatoScreenNavigation(navController: NavHostController) {
    composable(route = FormularioContato.rotaComArgumentos, arguments = FormularioContato.argumentos) {
        navBackStackEntry ->
        navBackStackEntry.arguments?.getLong(
            ID_CONTATO
        )?.let { idContato ->

            val viewModel = hiltViewModel<FormularioContatoViewModel>()
            val state by viewModel.uiState.collectAsState()
            val context = LocalContext.current

            //esse LaunchedEffect(Unit){ } é muito importante:
            //faz com que a mudanca do onValueChange do OutlinedTextField do Composable (que desencadeia recomposicao)
            //nao execute de novo essa linha de preencher o Contato inteiro do uiState usando os dados do Banco de Dados
            LaunchedEffect(Unit) {
                viewModel.receberIdPeloNavigation(idContato)
            }

            LaunchedEffect(state.aniversario) {
                viewModel.defineTextoAniversario(
                    context.getString(R.string.aniversario)
                )
            }

            val scope = rememberCoroutineScope()

            FormularioContatoTela(state = state, onClickSalvar = {
                    scope.launch {
                        viewModel.onSaveClick()
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