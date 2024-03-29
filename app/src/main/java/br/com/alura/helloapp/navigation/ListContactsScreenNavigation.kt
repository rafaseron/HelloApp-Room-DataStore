package br.com.alura.helloapp.navigation

import androidx.compose.runtime.LaunchedEffect
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
import br.com.alura.helloapp.localData.preferences.TypeSafetyPreferences.key.userIsAuthenticated
import br.com.alura.helloapp.localData.preferences.dataStore
import br.com.alura.helloapp.ui.screens.ListaContatosTela
import br.com.alura.helloapp.ui.viewmodels.ListaContatosViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

fun NavGraphBuilder.listContactsScreenNavigation(navController: NavHostController) {
    navigation(startDestination = DestinosHelloApp.ListaContatos.rota, route = DestinosHelloApp.HomeGraph.rota,) {

        composable(route = DestinosHelloApp.ListaContatos.rota) {
            val viewModel = hiltViewModel<ListaContatosViewModel>()
            val state by viewModel.uiState.collectAsState()
            val scope = rememberCoroutineScope()
            val dataStore = LocalContext.current.dataStore
            LaunchedEffect(Unit) {
                //atualizar a lista recebida do banco de dados a cada navegacao nessa tela
                viewModel.updateContactList()

                //verificacao de persistencia de login
                scope.launch {
                    val prefereces = dataStore.data.first()
                    val chave = prefereces[booleanPreferencesKey(userIsAuthenticated)]
                    chave?.let {valor ->
                        if (!valor){ navController.navigate(route = DestinosHelloApp.LoginGraph.rota) }
                    }
                }

            }

            ListaContatosTela(
                state = state,
                onClickAbreDetalhes = { idContato ->
                    navController.navegaParaDetalhes(idContato)
                },
                onClickAbreCadastro = {
                    navController.navegaParaFormularioContato()
                },
                onClickDesloga = {
                    scope.launch {
                        val preferences = dataStore.data.first()
                        val chave =  preferences[booleanPreferencesKey(userIsAuthenticated)]
                        chave?.let { valor ->
                            if (valor) { dataStore.edit { preferences ->
                                preferences[booleanPreferencesKey(userIsAuthenticated)] = false
                                navController.navigate(route = DestinosHelloApp.LoginGraph.rota)
                            } }
                        }
                    }
                })
        }
    }
}

