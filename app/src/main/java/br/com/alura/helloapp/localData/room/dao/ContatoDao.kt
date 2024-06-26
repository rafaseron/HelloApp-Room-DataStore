package br.com.alura.helloapp.localData.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import br.com.alura.helloapp.localData.room.entities.Contato
import java.util.Date
import kotlinx.coroutines.flow.Flow

@Dao
interface ContatoDao {

    @Insert
    suspend fun insert(contato: Contato): Long  //podemos retornar em Long quantos dados foram inseridos

    @Update(onConflict = REPLACE)
    suspend fun update(contato: Contato): Int //podemos retornar em Int quantos dados foram atualizados

    @Delete
    suspend fun delete(contato: Contato)

    //Query são CONSULTAS no Banco de Dados usando SQL
    @Query("SELECT * FROM Contato") //tambem podemos adicionar WHERE e AND se necessario
    fun getAll(): Flow<List<Contato>>
    //WHERE ajudaria a pesquisar por um id especifico por exemplo. AND ajudaria a adicionar mais logica a consulta, se preciso

    @Query("SELECT * FROM Contato WHERE birthday = :dataDeHoje")
    suspend fun getAniversariantes(dataDeHoje: Date): List<Contato>

    @Query("SELECT * FROM Contato WHERE id = :idToSearch")
    fun getContactFromId(idToSearch: Long): Flow<Contato?>

}