package com.example.moviesexplorer.data.network

import com.example.moviesexplorer.data.db.entity.Movie
import com.example.moviesexplorer.data.network.response.PopularMoviesResponse
import com.example.moviesexplorer.data.network.response.TopMoviesResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val API_KEY = "45301140f50a799c1eaf1572675ae7e2";

interface MovieService {
    @GET("movie/popular")
    suspend fun getPopularMovies(): PopularMoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): TopMoviesResponse

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: Int): Movie

    companion object {
        private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        operator fun invoke(): MovieService {
            val interceptor = Interceptor { chain ->
                val url = chain.request().url.newBuilder()
                    .addQueryParameter("access_key", API_KEY)
                    .build()

                val request = chain.request().newBuilder().url(url).build()

                return@Interceptor chain.proceed(request)
            }

            val okHttp = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder().client(okHttp)
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(MovieService::class.java)
        }
    }
}