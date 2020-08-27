package com.example.moviesexplorer.data.network.response


import com.example.moviesexplorer.data.db.entity.Movie
import com.google.gson.annotations.SerializedName

data class TopRatedMoviesResponse(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)