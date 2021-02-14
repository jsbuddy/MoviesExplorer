package com.example.moviesexplorer.data.repository

import com.example.moviesexplorer.data.db.MovieDatabase
import com.example.moviesexplorer.data.db.entity.Movie
import com.example.moviesexplorer.data.network.MovieAPI

class MovieRepository(
    private val db: MovieDatabase,
    private val api: MovieAPI,
) {

    suspend fun getPopularMovies(page: Int) = api.getPopularMovies(page)

    suspend fun getTopRatedMovies(page: Int) = api.getTopRatedMovies(page)

    fun getSavedMovies() = db.movieDao().getMovies()

    suspend fun saveMovie(movie: Movie) = db.movieDao().upsert(movie)

    suspend fun saveMovies(movies: List<Movie>) = db.movieDao().insertMovies(movies)

    suspend fun deleteMovie(movie: Movie) = db.movieDao().delete(movie)
}