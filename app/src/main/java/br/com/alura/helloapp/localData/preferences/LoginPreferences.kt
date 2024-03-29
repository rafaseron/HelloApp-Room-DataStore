package br.com.alura.helloapp.localData.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "loginPreferences")

class TypeSafetyPreferences{
    object Key{
        val userIsAuthenticated = "userIsAuthenticated"
    }

}