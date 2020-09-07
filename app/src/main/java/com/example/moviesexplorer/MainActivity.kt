package com.example.moviesexplorer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.moviesexplorer.data.db.MovieDatabase
import com.example.moviesexplorer.data.repository.MovieRepository
import com.example.moviesexplorer.databinding.ActivityMainBinding
import com.example.moviesexplorer.ui.movie.MovieViewModel
import com.example.moviesexplorer.ui.movie.MovieViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var model: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieRepository = MovieRepository(MovieDatabase(this))
        val viewModelProviderFactory = MovieViewModelProviderFactory(movieRepository)
        model = ViewModelProvider(this, viewModelProviderFactory).get(MovieViewModel::class.java)
    }
}
