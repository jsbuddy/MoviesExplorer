package com.example.moviesexplorer.ui.home

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.moviesexplorer.R
import com.example.moviesexplorer.ui.popular.PopularFragment
import com.example.moviesexplorer.ui.toprated.TopRatedFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_popular,
    R.string.tab_text_top_rated
)

class SectionsPagerAdapter(
    private val context: Context, fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PopularFragment.newInstance()
            1 -> TopRatedFragment.newInstance()
            else -> PopularFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}