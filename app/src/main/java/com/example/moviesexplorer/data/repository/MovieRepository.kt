package com.example.moviesexplorer.data.repository

import com.example.moviesexplorer.data.db.MovieDatabase
import com.example.moviesexplorer.data.db.entity.Movie
import com.example.moviesexplorer.data.network.MovieService

class MovieRepository(private val db: MovieDatabase) {
    suspend fun getPopularMovies() = MovieService().getPopularMovies()

    suspend fun getTopRatedMovies() = MovieService().getTopRatedMovies()

    fun getSavedMovies() = db.movieDao().getMovies()

    suspend fun saveMovie(movie: Movie) = db.movieDao().upsert(movie)

    suspend fun deleteMovie(movie: Movie) = db.movieDao().delete(movie)
}