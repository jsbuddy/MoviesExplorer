package com.example.moviesexplorer.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.moviesexplorer.MainActivity
import com.example.moviesexplorer.R
import com.example.moviesexplorer.databinding.FragmentMovieBinding

class MovieFragment : Fragment() {
    private val args: MovieFragmentArgs by navArgs()

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var model: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        model = (activity as MainActivity).model

        setupToolbar()

        binding.movie = args.movie
        binding.favorite.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                model.saveMovie(args.movie)
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
        return binding.root
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }
}