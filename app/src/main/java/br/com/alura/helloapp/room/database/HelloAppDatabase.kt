package br.com.alura.helloapp.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.helloapp.room.typeConverters.TypeConverter
import br.com.alura.helloapp.room.dao.ContatoDao
import br.com.alura.helloapp.room.entities.Contato

@Database(entities = [Contato::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class HelloAppDatabase : RoomDatabase() {
    //como a classe HelloAppDatabase tem uma funcao abstrata dentro dela -> ela precisa se tornar uma classe abstrata.
    // Tambem, isso eh pre-requisito para herdar RoomDatabase -> ja que ela nao tem Corpo. Voce nao pode instanciar a RoomDatabase diretamente, mas pode herda-la
    // e adquirir uma instancia do Room Database com Room.databaseBuilder() -> isso ta no codigo fonte de RoomDatabase -> CMD+Click/CTRL+Click

    //como a funcao Dao nao tem Corpo -> ela é uma funcao abstrata
    abstract fun contatoDao(): ContatoDao

    // Uma @Database precisa ser uma classe absrata em Kotlin
    // Para criar instancias de algo abstrato, voce tem que colocar o que voce quer instanciar dentro do
    // escopo de companion object { }. Dessa forma, por exemplo, getDataBase fica acessivel

    companion object {
        private lateinit var INSTANCE: HelloAppDatabase
        fun getDatabase(context: Context): HelloAppDatabase {
            // verifica se o banco de dados esta inicializado. Se estiver, retorna. Se não, inicializa e retorna (Singleton)
            if (::INSTANCE.isInitialized) {
                return INSTANCE
            } else {
                INSTANCE = Room.databaseBuilder(
                    context,
                    HelloAppDatabase::class.java,
                    "helloApp.db"
                ).allowMainThreadQueries().build()
                return INSTANCE
            }
        }
    }


}