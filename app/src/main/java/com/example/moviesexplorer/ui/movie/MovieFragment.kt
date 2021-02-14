package com.example.moviesexplorer.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.moviesexplorer.R
import com.example.moviesexplorer.databinding.FragmentMovieBinding

class MovieFragment : Fragment(R.layout.fragment_movie) {

    private val args: MovieFragmentArgs by navArgs()
    private lateinit var binding: FragmentMovieBinding
    private val viewModel: MovieViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMovieBinding.bind(view)
        setupToolbar()
        binding.movie = args.movie
        binding.favorite.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                viewModel.saveMovie(args.movie)
                Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
            }
        }
        binding.share.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, args.movie.originalTitle)
                type = "text/plain"
            }
            startActivity(intent)
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }
}