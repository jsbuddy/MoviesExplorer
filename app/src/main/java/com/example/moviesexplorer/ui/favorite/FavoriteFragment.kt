package com.example.moviesexplorer.ui.favorite

import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesexplorer.NavGraphDirections
import com.example.moviesexplorer.R
import com.example.moviesexplorer.adapters.MovieRecyclerViewItemDecoration
import com.example.moviesexplorer.adapters.MoviesRecyclerAdapter
import com.example.moviesexplorer.databinding.FragmentFavoriteBinding
import com.example.moviesexplorer.ui.movie.MovieViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: MovieViewModel by activityViewModels()
    private lateinit var moviesRecyclerAdapter: MoviesRecyclerAdapter

    private var actionMode: ActionMode? = null
    private val actionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            val inflater = requireActivity().menuInflater
            inflater.inflate(R.menu.menu_favorites_action, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu) = false

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.menu_delete -> viewModel.deleteMovies(moviesRecyclerAdapter.selected.value!!)
            }
            mode.finish()
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            moviesRecyclerAdapter.reset()
            actionMode = null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFavoriteBinding.bind(view)
        initialize()
    }

    private fun initialize() {
        setupRecyclerView()
        setupSwipeToDelete()

        viewModel.getSavedMovies().observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                moviesRecyclerAdapter.differ.submitList(it)
                binding.moviesList.visibility = View.VISIBLE
                binding.empty.visibility = View.INVISIBLE
            } else {
                binding.moviesList.visibility = View.INVISIBLE
                binding.empty.visibility = View.VISIBLE
            }
        })
    }

    private fun setupRecyclerView() {
        moviesRecyclerAdapter = MoviesRecyclerAdapter(viewLifecycleOwner)
        binding.moviesList.apply {
            adapter = moviesRecyclerAdapter
            layoutManager = GridLayoutManager(activity, 2)
            addItemDecoration(MovieRecyclerViewItemDecoration())
        }
        moviesRecyclerAdapter.apply {
            setOnItemClickListener {
                val action = NavGraphDirections.actionGlobalMovieFragment(it)
                Navigation.findNavController(requireActivity(), R.id.mainNavHostFragment)
                    .navigate(action)
            }
            lifecycleScope.launchWhenStarted {
                mode.collectLatest {
                    when (it) {
                        is MoviesRecyclerAdapter.Mode.MultiSelect -> {
                            actionMode = requireActivity().startActionMode(actionModeCallback)
                        }
                        is MoviesRecyclerAdapter.Mode.Default -> {
                            actionMode?.finish()
                        }
                    }
                }
            }
            selected.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) actionMode?.title = "${it.size} item(s) selected"
            }
        }
    }

    private fun setupSwipeToDelete() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.ACTION_STATE_IDLE,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val movie = moviesRecyclerAdapter.differ.currentList[position]

                viewModel.deleteMovie(movie)
                Snackbar.make(requireView(), "Removed from favorites", Snackbar.LENGTH_SHORT)
                    .apply {
                        setAction("Undo") {
                            viewModel.saveMovie(movie)
                        }
                        show()
                    }
            }
        }
        ItemTouchHelper(callback).attachToRecyclerView(binding.moviesList)
    }
}