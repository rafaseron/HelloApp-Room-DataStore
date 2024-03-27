package br.com.alura.helloapp.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import br.com.alura.helloapp.ui.screens.ListaContatosTela
import br.com.alura.helloapp.ui.viewmodels.ListaContatosViewModel

fun NavGraphBuilder.listContactsScreenNavigation(navController: NavHostController) {
    navigation(startDestination = DestinosHelloApp.ListaContatos.rota, route = DestinosHelloApp.HomeGraph.rota,) {

        composable(route = DestinosHelloApp.ListaContatos.rota) {
            val viewModel = hiltViewModel<ListaContatosViewModel>()
            val state by viewModel.uiState.collectAsState()
            viewModel.updateContactList()

            ListaContatosTela(
                state = state,
                onClickAbreDetalhes = { idContato ->
                    navController.navegaParaDetalhes(idContato)
                },
                onClickAbreCadastro = {
                    navController.navegaParaFormularioContato()
                },
                onClickDesloga = {


                })
        }
    }
}

