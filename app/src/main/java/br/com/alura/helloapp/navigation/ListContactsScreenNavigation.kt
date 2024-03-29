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
import br.com.alura.helloapp.localData.preferences.TypeSafetyPreferences.Key.AUTHENTICATED
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
                    //TODO mover esse codigo de onClickLogout pro ViewModel -> criando uma nova funcao
                    //TODO executar essa nova funcao aqui dentro com viewModel.executarfuncao()
                    //TODO -> injetar dependencias no ViewModel com Hilt
                    scope.launch {
                        val preferences = dataStore.data.first()
                        val chave =  preferences[booleanPreferencesKey(AUTHENTICATED)]
                        chave?.let { valor ->
                            if (valor) { dataStore.edit { preferences ->
                                preferences[booleanPreferencesKey(AUTHENTICATED)] = false
                                navController.navigate(route = DestinosHelloApp.LoginGraph.rota)
                            } }
                        }
                    }
                })
        }
    }
}

