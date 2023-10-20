package com.otarbakh.rickyandmorty.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "episodes")
data class EpisodesEntity(
    val air_date: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val episode: String,
    val url: String
)