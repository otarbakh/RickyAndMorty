package com.otarbakh.rickyandmorty.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.otarbakh.rickyandmorty.data.database.model.CharactersEntity

@Database(entities = [CharactersEntity::class], version = 1)
abstract class RickAndMortyDatabase : RoomDatabase(){
    abstract val charactersDao:RickAndMortyDao
}