package com.example.moviesexplorer.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.moviesexplorer.R
import com.example.moviesexplorer.adapters.BottomNavigationStateAdapter
import com.example.moviesexplorer.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var bottomNavigationStateAdapter: BottomNavigationStateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)

        binding.bottomAppBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.popularFragment -> {
                    binding.pager.setCurrentItem(0, false)
                }
                R.id.topRatedFragment -> {
                    binding.pager.setCurrentItem(1, false)
                }
                R.id.favoriteFragment -> {
                    binding.pager.setCurrentItem(2, false)
                }
            }
            true
        }

        bottomNavigationStateAdapter = BottomNavigationStateAdapter(this)
        binding.pager.adapter = bottomNavigationStateAdapter
        binding.pager.isUserInputEnabled = false
    }
}
