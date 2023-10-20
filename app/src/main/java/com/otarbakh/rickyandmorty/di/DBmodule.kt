package com.otarbakh.rickyandmorty.di

import android.content.Context
import androidx.room.Room
import com.otarbakh.rickyandmorty.data.database.RickAndMortyDatabase
import com.otarbakh.rickyandmorty.data.database.dao.CharactersDao
import com.otarbakh.rickyandmorty.data.database.dao.EpisodesDao
import com.otarbakh.rickyandmorty.data.database.dao.LocationsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBmodule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context): RickAndMortyDatabase {
        return Room.databaseBuilder(
            context,
            RickAndMortyDatabase::class.java,"characters_db",
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCharsDao(db: RickAndMortyDatabase): CharactersDao {
        return db.charactersDao
    }
    @Singleton
    @Provides
    fun provideEpsDao(db: RickAndMortyDatabase): EpisodesDao {
        return db.episodesDao
    }
    @Singleton
    @Provides
    fun provideLocationsDao(db: RickAndMortyDatabase): LocationsDao {
        return db.locationsDao
    }
    
}