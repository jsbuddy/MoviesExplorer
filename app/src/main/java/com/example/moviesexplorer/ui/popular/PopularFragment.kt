package com.example.moviesexplorer.ui.popular

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesexplorer.NavGraphDirections
import com.example.moviesexplorer.R
import com.example.moviesexplorer.adapters.MovieRecyclerViewItemDecoration
import com.example.moviesexplorer.adapters.MoviesPagerAdapter
import com.example.moviesexplorer.databinding.FragmentPopularBinding
import com.example.moviesexplorer.ui.movie.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PopularFragment : Fragment(R.layout.fragment_popular) {

    private lateinit var binding: FragmentPopularBinding

    private val moviesPagerAdapter = MoviesPagerAdapter()
    private val viewModel: MovieViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        retainInstance = true
        lifecycleScope.launchWhenStarted {
            viewModel.popularMovies.collectLatest {
                moviesPagerAdapter.submitData(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPopularBinding.bind(view)
        initialize()
    }

    private fun initialize() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.moviesList.apply {
            adapter = moviesPagerAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(MovieRecyclerViewItemDecoration())
        }
        moviesPagerAdapter.apply {
            setOnItemClickListener {
                val action = NavGraphDirections.actionGlobalMovieFragment(it)
                Navigation.findNavController(requireActivity(), R.id.mainNavHostFragment)
                    .navigate(action)
            }
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }
}