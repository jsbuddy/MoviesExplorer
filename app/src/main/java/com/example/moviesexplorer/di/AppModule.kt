package com.example.moviesexplorer.di

import android.content.Context
import com.example.moviesexplorer.data.db.MovieDatabase
import com.example.moviesexplorer.data.network.MovieAPI
import com.example.moviesexplorer.data.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext context: Context) = MovieDatabase(context)

    @Singleton
    @Provides
    fun provideMovieDao(db: MovieDatabase) = db.movieDao()

    @Singleton
    @Provides
    fun provideMovieAPI() = MovieAPI()

    @Singleton
    @Provides
    fun provideMovieRepository(db: MovieDatabase, api: MovieAPI) = MovieRepository(db, api)
}