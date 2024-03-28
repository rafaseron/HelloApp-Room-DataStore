package br.com.alura.helloapp.localData.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Usuario")
data class Usuario (@ColumnInfo(name = "nome")
                    val nome: String = "",
                    @PrimaryKey
                    @ColumnInfo(name = "usuario")
                    val usuario: String = "",
                    @ColumnInfo(name = "senha")
                    val senha: String = "")