package com.example.moviesexplorer.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesexplorer.data.db.entity.Movie
import com.example.moviesexplorer.data.models.MovieCategory
import com.example.moviesexplorer.data.repository.MovieRepository

class TopRatedMoviesRemotePagingSource(
    private val repository: MovieRepository,
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        val response = repository.getTopRatedMovies(page)
        return if (response.isSuccessful) {
            response.body()?.let {
                LoadResult.Page(
                    data = it.results.map { movie ->
                        movie.category = MovieCategory.TOP_RATED
                        movie
                    },
                    prevKey = null,
                    nextKey = it.page + 1
                )
            } ?: LoadResult.Error(Throwable())
        } else {
            return LoadResult.Error(Throwable())
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}