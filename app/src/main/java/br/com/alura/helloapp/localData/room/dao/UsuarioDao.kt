package br.com.alura.helloapp.localData.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.alura.helloapp.localData.room.entities.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertUser(usuario: Usuario)

    @Delete
    suspend fun deleteUser(usuario: Usuario)

    @Update
    suspend fun updateUser(usuario: Usuario)

    @Query("SELECT * FROM Usuario")
    fun getAllUsers(): Flow<List<Usuario>>

    @Query("SELECT * FROM Usuario WHERE usuario = :username")
    suspend fun searchUserFromUsername(username: String): Usuario?

    @Query("SELECT * FROM Usuario WHERE usuario = :username AND senha = :senha")
    suspend fun authenticateUser(username: String, senha: String): Usuario?

}