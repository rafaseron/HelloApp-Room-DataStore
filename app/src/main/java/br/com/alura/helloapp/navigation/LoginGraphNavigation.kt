package br.com.alura.helloapp.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import br.com.alura.helloapp.ui.screens.FormularioLoginTela
import br.com.alura.helloapp.ui.viewmodels.FormularioLoginViewModel
import br.com.alura.helloapp.ui.screens.LoginTela
import br.com.alura.helloapp.ui.viewmodels.LoginViewModel

fun NavGraphBuilder.loginGraphNavigation(navController: NavHostController) {
    navigation(startDestination = DestinosHelloApp.Login.rota, route = DestinosHelloApp.LoginGraph.rota) {

        composable(route = DestinosHelloApp.Login.rota) {

            val viewModel = hiltViewModel<LoginViewModel>()
            val state by viewModel.uiState.collectAsState()

            if (state.logado) {
                LaunchedEffect(Unit) {
                    navController.navegaLimpo(DestinosHelloApp.HomeGraph.rota)
                }
            }

            LoginTela(
                state = state,
                onClickLogar = {
                    viewModel.tentaLogar()
                    if (state.logado){ navController.navigate(route = DestinosHelloApp.ListaContatos.rota) }
                },
                onClickCriarLogin = {
                    navController.navigate(DestinosHelloApp.FormularioLogin.rota)
                }
            )
        }

        composable(route = DestinosHelloApp.FormularioLogin.rota) {

            val viewModel = hiltViewModel<FormularioLoginViewModel>()
            val state by viewModel.uiState.collectAsState()

            FormularioLoginTela(state = state,
                onSalvar = {
                    viewModel.criarUsuario()
                    navController.navegaLimpo(DestinosHelloApp.Login.rota)
                }
            )
        }
    }
}