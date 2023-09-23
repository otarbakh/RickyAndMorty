package com.otarbakh.rickyandmorty.di

import com.otarbakh.rickyandmorty.common.Constants
import com.otarbakh.rickyandmorty.data.service.RickyAndMortyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRickyAndMorty(): RickyAndMortyService =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RickyAndMortyService::class.java)
}