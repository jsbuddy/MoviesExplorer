package com.example.moviesexplorer.ui.popular

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesexplorer.MainActivity
import com.example.moviesexplorer.adapters.MoviesRecyclerAdapter
import com.example.moviesexplorer.databinding.FragmentPopularBinding
import com.example.moviesexplorer.ui.movie.MovieViewModel
import com.example.moviesexplorer.utils.Resource

class PopularFragment : Fragment() {

    companion object {
        fun newInstance() = PopularFragment()
    }

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesRecyclerAdapter: MoviesRecyclerAdapter
    private lateinit var model: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        model = (activity as MainActivity).model
        setupRecyclerView()

        model.popularMovies.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    it.data?.let { moviesResponse ->
                        moviesRecyclerAdapter.differ.submitList(moviesResponse.results)
                        binding.moviesList.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    it.message?.let { message ->
                        Log.e("NetworkError", message)
                    }
                }
            }
        })

        return binding.root
    }

    private fun setupRecyclerView() {
        moviesRecyclerAdapter = MoviesRecyclerAdapter()
        binding.moviesList.apply {
            adapter = moviesRecyclerAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }
}