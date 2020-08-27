package com.example.moviesexplorer.data.db

import androidx.room.TypeConverter
import com.example.moviesexplorer.data.models.Genre

class Converters {
    @TypeConverter
    fun fromGenre(genre: Genre?): String? {
        return genre?.name
    }

    @TypeConverter
    fun toGenre(value: String?): Genre? {
        return value?.let {
            Genre(name = it)
        }
    }
}