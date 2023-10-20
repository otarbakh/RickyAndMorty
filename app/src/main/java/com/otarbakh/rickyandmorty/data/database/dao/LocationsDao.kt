package com.otarbakh.rickyandmorty.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity
import com.otarbakh.rickyandmorty.data.database.model.LocationsEntity

@Dao
interface LocationsDao {
    @Query("SELECT * FROM locations")
    fun getLocations(): PagingSource<Int, LocationsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLocations(characters: List<LocationsEntity>)

}