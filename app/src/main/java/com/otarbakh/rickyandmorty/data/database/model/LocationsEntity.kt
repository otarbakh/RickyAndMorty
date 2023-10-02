package com.otarbakh.rickyandmorty.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class LocationsEntity(
    val gender: String?,
    @PrimaryKey(false)
    val id: Int?,
    val image: String?,
    val name: String?,
)