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