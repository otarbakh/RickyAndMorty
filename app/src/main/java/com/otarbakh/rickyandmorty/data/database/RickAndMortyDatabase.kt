package com.otarbakh.rickyandmorty.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity
import com.otarbakh.rickyandmorty.data.database.model.EpisodesEntity
import com.otarbakh.rickyandmorty.data.database.model.LocationsEntity

@Database(
    entities = [
        CharactersEntity::class,
        LocationsEntity::class,
        EpisodesEntity::class
    ], version = 2
)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract val charactersDao: RickAndMortyDao
}