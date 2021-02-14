package com.example.moviesexplorer.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MovieRecyclerViewItemDecoration() : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = 5
        outRect.bottom = 5
        outRect.left = 5
        outRect.right = 5
        parent.setPadding(5, 5, 5, 5)
    }
}