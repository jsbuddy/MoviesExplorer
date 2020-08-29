package com.example.moviesexplorer.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviesexplorer.data.db.entity.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movie: Movie): Long

    @Query("SELECT * FROM movies")
    fun getMovies(): LiveData<List<Movie>>

    @Delete
    suspend fun delete(movie: Movie)
}