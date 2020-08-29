package com.example.moviesexplorer.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("image")
fun bindImage(view: ImageView, url: String) {
    Glide.with(view.context)
        .load("https://image.tmdb.org/t/p/w342${url}")
        .into(view)
}