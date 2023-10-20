package com.otarbakh.rickyandmorty.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.otarbakh.rickyandmorty.data.database.dao.CharactersDao
import com.otarbakh.rickyandmorty.data.database.dao.EpisodesDao
import com.otarbakh.rickyandmorty.data.database.dao.LocationsDao
import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity
import com.otarbakh.rickyandmorty.data.database.model.EpisodesEntity
import com.otarbakh.rickyandmorty.data.database.model.LocationsEntity

@Database(
    entities = [
        CharactersEntity::class,
        LocationsEntity::class,
        EpisodesEntity::class
    ], version = 8
)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract val charactersDao: CharactersDao
    abstract val locationsDao: LocationsDao
    abstract val episodesDao: EpisodesDao
}