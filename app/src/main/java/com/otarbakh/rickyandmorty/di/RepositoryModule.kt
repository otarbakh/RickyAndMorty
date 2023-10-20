package com.otarbakh.rickyandmorty.di

import com.otarbakh.rickyandmorty.data.repository.CharactersRepositoryImpl
import com.otarbakh.rickyandmorty.data.repository.EpisodesRepositoryImpl
import com.otarbakh.rickyandmorty.data.repository.LocationsRepositoryImpl
import com.otarbakh.rickyandmorty.domain.repository.CharactersRepository
import com.otarbakh.rickyandmorty.domain.repository.EpisodesRepository
import com.otarbakh.rickyandmorty.domain.repository.LocationsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class epositoryModule {

    @Binds
    @Singleton
    abstract fun bindCharactersRepository(
        charactersRepository: CharactersRepositoryImpl
    ): CharactersRepository

    @Binds
    @Singleton
    abstract fun bindLocationsRepository(
        locationsRepository: LocationsRepositoryImpl
    ): LocationsRepository

    @Binds
    @Singleton
    abstract fun bindEpisodesRepository(
        episodesRepository: EpisodesRepositoryImpl
    ): EpisodesRepository

}