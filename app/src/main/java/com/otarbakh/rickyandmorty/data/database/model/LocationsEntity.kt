package com.otarbakh.rickyandmorty.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.otarbakh.rickyandmorty.domain.model.LocationsDomain

@Entity(tableName = "locations")
data class LocationsEntity(
    val type: String?,
    @PrimaryKey(false)
    val id: Int?,
    val name: String?,
)

fun LocationsEntity.toDomain(): LocationsDomain {
    return LocationsDomain(
        type,id,name
    )
}