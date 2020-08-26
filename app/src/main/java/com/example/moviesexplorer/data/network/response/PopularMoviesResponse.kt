package com.example.moviesexplorer.data.network.response


import com.example.moviesexplorer.data.models.MovieLite
import com.google.gson.annotations.SerializedName

data class PopularMoviesResponse(
    val page: Int,
    val results: List<MovieLite>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)