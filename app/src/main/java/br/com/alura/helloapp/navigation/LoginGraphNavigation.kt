package br.com.alura.helloapp.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import br.com.alura.helloapp.localData.preferences.TypeSafetyPreferences.Key.AUTHENTICATED
import br.com.alura.helloapp.localData.preferences.dataStore
import br.com.alura.helloapp.ui.screens.FormularioLoginTela
import br.com.alura.helloapp.ui.viewmodels.FormularioLoginViewModel
import br.com.alura.helloapp.ui.screens.LoginTela
import br.com.alura.helloapp.ui.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

fun NavGraphBuilder.loginGraphNavigation(navController: NavHostController) {
    navigation(startDestination = DestinosHelloApp.Login.rota, route = DestinosHelloApp.LoginGraph.rota) {

        composable(route = DestinosHelloApp.Login.rota) {

            val viewModel = hiltViewModel<LoginViewModel>()
            val state by viewModel.uiState.collectAsState()

            val scope = rememberCoroutineScope()
            val dataStore = LocalContext.current.dataStore

            LoginTela(
                state = state,
                onClickLogar = {
                    viewModel.tentaLogar()
                    if (state.logado){
                        navController.navigate(route = DestinosHelloApp.ListaContatos.rota)
                        scope.launch {
                            dataStore.edit { preferences ->
                                preferences[booleanPreferencesKey(AUTHENTICATED)] = true
                            }
                        }
                    }
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