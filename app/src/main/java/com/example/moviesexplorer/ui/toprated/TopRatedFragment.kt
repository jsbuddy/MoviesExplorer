package com.example.moviesexplorer.ui.toprated

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesexplorer.NavGraphDirections
import com.example.moviesexplorer.R
import com.example.moviesexplorer.adapters.MovieRecyclerViewItemDecoration
import com.example.moviesexplorer.adapters.MoviesPagerAdapter
import com.example.moviesexplorer.databinding.FragmentTopRatedBinding
import com.example.moviesexplorer.ui.movie.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class TopRatedFragment : Fragment(R.layout.fragment_top_rated) {

    private lateinit var binding: FragmentTopRatedBinding

    private lateinit var moviesPagerAdapter: MoviesPagerAdapter
    private val viewModel: MovieViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentTopRatedBinding.bind(view)
        initialize()
    }

    private fun initialize() {
        setupRecyclerView()
        lifecycleScope.launchWhenStarted {
            viewModel.topRatedMovies.collectLatest {
                moviesPagerAdapter.submitData(it)
            }
        }
    }

    private fun setupRecyclerView() {
        moviesPagerAdapter = MoviesPagerAdapter()
        binding.moviesList.apply {
            adapter = moviesPagerAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(MovieRecyclerViewItemDecoration())
        }
        moviesPagerAdapter.setOnItemClickListener {
            val action = NavGraphDirections.actionGlobalMovieFragment(it)
            Navigation.findNavController(requireActivity(), R.id.mainNavHostFragment)
                .navigate(action)
        }
    }
}

