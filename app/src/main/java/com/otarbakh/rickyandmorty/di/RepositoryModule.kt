package com.otarbakh.rickyandmorty.di

import com.otarbakh.rickyandmorty.data.repository.RickAndMortyRepositoryImpl
import com.otarbakh.rickyandmorty.domain.repository.RickAndMortyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRickAndMortyRepository(
        rickAndMortyRepository: RickAndMortyRepositoryImpl
    ): RickAndMortyRepository

}