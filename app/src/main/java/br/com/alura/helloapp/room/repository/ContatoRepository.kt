package br.com.alura.helloapp.room.repository

import android.content.Context
import br.com.alura.helloapp.room.database.HelloAppDatabase
import br.com.alura.helloapp.room.entities.Contato
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.util.Date

class ContatoRepository (context: Context) {

    private val contatoDataBase = HelloAppDatabase.getDatabase(context).contatoDao() /* essa linha é basicamente como ser a Ponte entre os arquivos
    // isolados que temos de HelloAppDatabase + ContatoDao.

    Analogia: o Repository é um terceirizador de servicço, HelloAppDatabase é um empregador e o ContatoDao é alguem que sabe trabalhar
    -> Com esta linha 'contatoDataBase' Repository está usando o trabalhador Dao para fazer o servicço para o empregador DataBase
    Igual uma empresa de Consultoria faz.

    O Repository então usa o Dao para realizar operações no Banco de Dados
     */

    suspend fun insertOnDatabase(contato: Contato): Boolean{
        return contatoDataBase.insert(contato) > 0
    }

    suspend fun updateOnDatabasse(contato: Contato): Boolean{
        return contatoDataBase.update(contato) > 0
    }

    suspend fun deleteOnDatabase(id: Long){
        val contatoFiltrado = searchContactFromId(id)
        val tirarFlow = contatoFiltrado.first()

        tirarFlow?.let {
            contatoDataBase.delete(it)
        }
    }

    suspend fun getAllContacts(): Flow<List<Contato>> {
        return contatoDataBase.getAll()
    }

    suspend fun searchContactFromId(id: Long): Flow<Contato?>{
        return contatoDataBase.getContactFromId(id)
    }

    suspend fun getAniversariantes(data: Date): List<Contato>{
        return contatoDataBase.getAniversariantes(data)
    }


}