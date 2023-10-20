package com.otarbakh.rickyandmorty.data.database.dao

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
interface EpisodesDao {
    @Query("SELECT * FROM episodes")
    fun getEpisodes(): PagingSource<Int, EpisodesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEpisodes(episodes: List<EpisodesEntity>)

}