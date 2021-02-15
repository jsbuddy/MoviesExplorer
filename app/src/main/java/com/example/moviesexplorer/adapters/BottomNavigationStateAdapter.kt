package com.example.moviesexplorer.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviesexplorer.ui.favorite.FavoriteFragment
import com.example.moviesexplorer.ui.popular.PopularFragment
import com.example.moviesexplorer.ui.toprated.TopRatedFragment

class BottomNavigationStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PopularFragment()
            1 -> TopRatedFragment()
            2 -> FavoriteFragment()
            else -> PopularFragment()
        }
    }
}