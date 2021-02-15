package com.example.moviesexplorer.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.moviesexplorer.data.PopularMoviesRemotePagingSource
import com.example.moviesexplorer.data.TopRatedMoviesRemotePagingSource
import com.example.moviesexplorer.data.db.entity.Movie
import com.example.moviesexplorer.data.repository.MovieRepository
import com.example.moviesexplorer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {

    val popularMovies = Pager(config = PagingConfig(pageSize = 20)) {
        PopularMoviesRemotePagingSource(repository)
    }.flow

    val topRatedMovies = Pager(config = PagingConfig(pageSize = 20)) {
        TopRatedMoviesRemotePagingSource(repository)
    }.flow

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState = _uiState.asStateFlow()

    sealed class UiState {
        object Empty : UiState()
        object Loading : UiState()
        object Success : UiState()
    }

    fun getSavedMovies() = repository.getSavedMovies()

    fun saveMovie(movie: Movie) = viewModelScope.launch {
        repository.saveMovie(movie)
    }

    fun deleteMovie(movie: Movie) = viewModelScope.launch {
        repository.deleteMovie(movie)
    }

    private fun <T> handleResponse(response: Response<T>): Resource<T> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun deleteMovies(movies: List<Movie>) = viewModelScope.launch {
        repository.deleteMovies(movies)
    }
}