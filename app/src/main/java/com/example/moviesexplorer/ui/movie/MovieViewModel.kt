package com.example.moviesexplorer.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesexplorer.data.db.entity.Movie
import com.example.moviesexplorer.data.network.response.PopularMoviesResponse
import com.example.moviesexplorer.data.network.response.TopRatedMoviesResponse
import com.example.moviesexplorer.data.repository.MovieRepository
import com.example.moviesexplorer.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
    val popularMovies = MutableLiveData<Resource<PopularMoviesResponse>>()
    val topRatedMovies = MutableLiveData<Resource<TopRatedMoviesResponse>>()

    init {
        getPopularMovies()
        getTopRatedMovies()
    }

    private fun getPopularMovies() = viewModelScope.launch {
        popularMovies.postValue(Resource.Loading())
        val response = repository.getPopularMovies()
        popularMovies.postValue(handleResponse(response))
    }

    private fun getTopRatedMovies() = viewModelScope.launch {
        topRatedMovies.postValue(Resource.Loading())
        val response = repository.getTopRatedMovies()
        topRatedMovies.postValue(handleResponse(response))
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
}