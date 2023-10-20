package com.otarbakh.rickyandmorty.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharactersEntity(
    val gender: String?,
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    val name: String?,
    val image: String?,
    val status:String?
)


