package br.com.alura.helloapp.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.alura.helloapp.room.entities.Contato

@Dao
interface ContatoDao {

    @Insert
    fun insere(contato: Contato)

    @Query("SELECT * FROM Contato")
    fun buscaTodos(): List<Contato>
}