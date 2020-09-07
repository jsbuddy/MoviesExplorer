package com.example.moviesexplorer.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesexplorer.MainActivity
import com.example.moviesexplorer.NavGraphDirections
import com.example.moviesexplorer.R
import com.example.moviesexplorer.adapters.MovieRecyclerViewItemDecoration
import com.example.moviesexplorer.adapters.MoviesRecyclerAdapter
import com.example.moviesexplorer.databinding.FragmentFavoriteBinding
import com.example.moviesexplorer.ui.movie.MovieViewModel
import com.google.android.material.snackbar.Snackbar

class FavoriteFragment : Fragment() {
    private lateinit var model: MovieViewModel

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesRecyclerAdapter: MoviesRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        model = (activity as MainActivity).model
        setupRecyclerView()
        setupSwipeToDelete()
        model.getSavedMovies().observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                moviesRecyclerAdapter.differ.submitList(it)
                binding.moviesList.visibility = View.VISIBLE
                binding.empty.visibility = View.INVISIBLE
            } else {
                binding.moviesList.visibility = View.INVISIBLE
                binding.empty.visibility = View.VISIBLE
            }
        })
        return binding.root
    }

    private fun setupRecyclerView() {
        moviesRecyclerAdapter = MoviesRecyclerAdapter()
        binding.moviesList.apply {
            adapter = moviesRecyclerAdapter
            layoutManager = GridLayoutManager(activity, 2)
            addItemDecoration(MovieRecyclerViewItemDecoration(40))
        }

        moviesRecyclerAdapter.setOnItemClickListener {
            val action = NavGraphDirections.actionGlobalMovieFragment(it)
            Navigation.findNavController(requireActivity(), R.id.mainNavHostFragment)
                .navigate(action)
        }
    }

    private fun setupSwipeToDelete() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val movie = moviesRecyclerAdapter.differ.currentList[position]

                model.deleteMovie(movie)
                Snackbar.make(requireView(), "Removed from favorites", Snackbar.LENGTH_SHORT)
                    .apply {
                        setAction("Undo") {
                            model.saveMovie(movie)
                        }
                        show()
                    }
            }
        }
        ItemTouchHelper(callback).attachToRecyclerView(binding.moviesList)
    }
}