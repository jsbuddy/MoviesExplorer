package com.example.moviesexplorer.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.moviesexplorer.R
import com.example.moviesexplorer.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)

        val navHostFragment =
            childFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        binding.bottomAppBar.setupWithNavController(navHostFragment.navController)
    }
}
