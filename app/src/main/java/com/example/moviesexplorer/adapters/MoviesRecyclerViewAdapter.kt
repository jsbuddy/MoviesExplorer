package com.example.moviesexplorer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesexplorer.data.db.entity.Movie
import com.example.moviesexplorer.databinding.ListItemMovieBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MoviesRecyclerAdapter(
    private val lifecycleOwner: LifecycleOwner,
) : RecyclerView.Adapter<MoviesRecyclerAdapter.ViewHolder>() {

    private val _selected = MutableStateFlow<List<Movie>>(emptyList())
    val selected = _selected.asLiveData()

    private val _mode = MutableStateFlow<Mode>(Mode.Default)
    val mode = _mode.asStateFlow()

    sealed class Mode {
        object Default : Mode()
        object MultiSelect : Mode()
    }

    fun reset() {
        _selected.value = emptyList()
        _mode.value = Mode.Default
    }

    inner class ViewHolder(
        private val binding: ListItemMovieBinding,
        private val context: Context,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                    .into(movieImage)
                root.setOnClickListener { view ->
                    if (mode.value == Mode.MultiSelect) select(movie)
                    else onItemClickListener?.let { it(movie) }
                }
                root.setOnLongClickListener { view ->
                    _mode.value = Mode.MultiSelect
                    select(movie)
                    true
                }
                selected.observe(lifecycleOwner) {
                    root.isChecked = it.contains(movie)
                }
            }
        }

        private fun select(movie: Movie) {
            val list = _selected.value.toMutableList()
            if (list.contains(movie)) list.remove(movie)
            else list.add(movie)
            if (list.isEmpty()) reset()
            else _selected.value = list.toList()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemMovieBinding.inflate(inflater)
        return ViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie)
    }

    override fun getItemCount() = differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    private var onItemClickListener: ((Movie) -> Unit)? = null

    fun setOnItemClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }
}