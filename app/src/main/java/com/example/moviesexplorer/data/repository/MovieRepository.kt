package com.example.moviesexplorer.data.repository

import com.example.moviesexplorer.data.db.MovieDatabase
import com.example.moviesexplorer.data.network.MovieService

class MovieRepository(private val db: MovieDatabase) {
    suspend fun getPopularMovies() = MovieService().getPopularMovies()

    suspend fun getTopRatedMovies() = MovieService().getTopRatedMovies()
}