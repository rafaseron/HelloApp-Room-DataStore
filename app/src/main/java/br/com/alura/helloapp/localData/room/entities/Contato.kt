package br.com.alura.helloapp.localData.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Contato")
data class Contato(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "nome")
    val nome: String = "",
    @ColumnInfo(name = "lastName")
    val sobrenome: String = "",
    @ColumnInfo(name = "phone")
    val telefone: String = "",
    @ColumnInfo(name = "photo")
    val fotoPerfil: String = "",
    @ColumnInfo(name = "birthday")
    val aniversario: Date? = null,
)