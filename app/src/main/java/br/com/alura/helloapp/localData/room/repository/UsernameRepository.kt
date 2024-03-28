package br.com.alura.helloapp.localData.room.repository

import android.content.Context
import br.com.alura.helloapp.localData.room.database.HelloAppDatabase
import br.com.alura.helloapp.localData.room.entities.Usuario

class UsernameRepository(context: Context) {

    private val usernameDatabase = HelloAppDatabase.getDatabase(context).usuarioDao()

    suspend fun createNewUser(usuario: Usuario){
        //verificar se usuario ja existe
        val checkUserIfExists = usernameDatabase.searchUserFromUsername(usuario.usuario)
        checkUserIfExists?.let {
            //nada a fazer
        } ?: return usernameDatabase.insertUser(usuario)
    }

    suspend fun searchUserByUsername(username: String): Usuario?{
        return usernameDatabase.searchUserFromUsername(username)
    }

    suspend fun verificarUsuario(username: String, password: String): Boolean{
        return usernameDatabase.authenticateUser(username = username, senha = password) != null
    }
}