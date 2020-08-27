package com.example.moviesexplorer.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesexplorer.data.repository.MovieRepository

@Suppress("UNCHECKED_CAST")
class MovieViewModelProviderFactory(
    private val movieRepository: MovieRepository,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(movieRepository) as T
    }
}