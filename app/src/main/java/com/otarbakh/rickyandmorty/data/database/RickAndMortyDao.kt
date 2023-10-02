package com.otarbakh.rickyandmorty.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity
import com.otarbakh.rickyandmorty.data.database.model.EpisodesEntity
import com.otarbakh.rickyandmorty.data.database.model.LocationsEntity

@Dao
interface RickAndMortyDao {

    @Query("SELECT * FROM characters")
    fun getCharacters(): PagingSource<Int, CharactersEntity>

    @Query("SELECT * FROM episodes")
    fun getEpisodes(): PagingSource<Int, EpisodesEntity>

    @Query("SELECT * FROM locations")
    fun getLocations(): PagingSource<Int, LocationsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: CharactersEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characters: List<CharactersEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEpisodes(characters: List<EpisodesEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLocations(characters: List<LocationsEntity>)

    @Delete
    fun delete(character: CharactersEntity)

    @Delete
    fun deleteAll(characters: List<CharactersEntity>)

    @Update
    fun updateTask(character: CharactersEntity)

    @Query("DELETE FROM characters")
    fun deleteAll()

    @Query("DELETE FROM characters WHERE id IN (SELECT id FROM characters ORDER BY id DESC LIMIT -1 OFFSET 40)")
    suspend fun deleteOldEntries()
}