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
    /*como a classe HelloAppDatabase tem uma funcao abstrata dentro dela -> ela precisa se tornar uma classe abstrata.
         Tambem, isso eh pre-requisito para herdar RoomDatabase -> ja que ela nao tem Corpo. Voce nao pode instanciar a RoomDatabase diretamente, mas pode herda-la
         e adquirir uma instancia do Room Database com Room.databaseBuilder() -> isso ta no codigo fonte de RoomDatabase -> CMD+Click/CTRL+Click */

    //como a funcao Dao nao tem Corpo -> ela é uma funcao abstrata
    abstract fun contatoDao(): ContatoDao
    /*a funcao abstrata (funcao sem corpo) contatoDao esta herdando uma Interface, que é a ContatoDao
        isso faz com que ela ganhe todas as funções que estao definidas no 'interface ContatoDao{ }'
        lembrando que uma Interface pode ter metodos ou variaveis, mas nao pode ter variavies com valores já inicializados. Ela serve como um molde sem valores inicializados */

    /* quem 'instancia' a funcao abstrata contatoDao (como na  private val contatoDataBase em ContatoRepository) na verdade nao instancia diretamente a funcao abstrata,
        mas sim, recebe uma instancia de uma classe gerada automaticamente que implementa a interface ContatoDao.
        Por isso, pode pareceber que a funcao abstrata pode ser instanciada, mas igual a classe abstrata, nao pode. */

    /* Uma @Database precisa ser uma classe absrata em Kotlin -> o que significa que ela só pode ser herdada e nunca instanciada
         Porem queremos uma instancia do Banco de Dados Room (que tambem eh uma classe abstrata no seu codigo fonte).
         Como conseguir sua instancia sem instanciar a classe?: CONTEUDO ABAIXO */

    /* Para criar instancias de algo abstrato, voce tem que colocar o que voce quer disponibilizar para ser instanciado dentro do
         escopo de companion object { }. Dessa forma, por exemplo, getDataBase fica acessivel
        -> ou seja, quem acessa o conteudo do 'companion object' instancia apenas o conteudo dele, sem instanciar a classe (pois nao eh possivel, eh abstrata)
         tambem tem como disponibilizar por 'object nomeDoObjeto{ }'. companion object eh acessado direto sem o nome do objeto, e so pode ter um por classe. */

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