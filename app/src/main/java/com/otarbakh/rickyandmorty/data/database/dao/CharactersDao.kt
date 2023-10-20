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
interface CharactersDao {

    @Query("SELECT * FROM characters")
    fun getCharacters(): PagingSource<Int, CharactersEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characters: List<CharactersEntity>)

    @Delete
    fun delete(character: CharactersEntity)

    @Delete
    fun deleteAll(characters: List<CharactersEntity>)

    @Update
    fun updateTask(character: CharactersEntity)

    @Query("DELETE FROM characters")
    fun deleteAll()

}