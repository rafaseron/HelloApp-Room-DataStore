package br.com.alura.helloapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.helloapp.localData.preferences.TypeSafetyPreferences.Key.AUTHENTICATED
import br.com.alura.helloapp.localData.preferences.dataStore
import br.com.alura.helloapp.ui.viewmodels.AppState
import br.com.alura.helloapp.ui.viewmodels.SplashScreenViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

fun NavGraphBuilder.splashScreenNavigation(navController: NavHostController) {
    composable(route = DestinosHelloApp.SplashScreen.rota) {

        val viewModel = hiltViewModel<SplashScreenViewModel>()
        val state by viewModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()
        val dataStore = LocalContext.current.dataStore
        val preferencesFlow = dataStore.data
        LaunchedEffect(Unit) {
            scope.launch {
                val preferences = preferencesFlow.first()
                when(preferences[booleanPreferencesKey(AUTHENTICATED)]){
                    true -> viewModel.atualizarAppState(AppState.Logado)
                    false -> viewModel.atualizarAppState(AppState.Deslogado)
                    else -> viewModel.atualizarAppState(AppState.Carregando)
                }
            }
        }

        when (state.appState) {
            AppState.Carregando -> Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            AppState.Deslogado -> {
                LaunchedEffect(Unit) {
                    navController.navegaLimpo(DestinosHelloApp.Login.rota)
                }
            }
            AppState.Logado -> {
                LaunchedEffect(Unit) {
                    navController.navegaLimpo(DestinosHelloApp.HomeGraph.rota)
                }
            }
        }
    }
}

