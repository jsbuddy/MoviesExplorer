package com.example.moviesexplorer.data.db

import androidx.room.TypeConverter
import com.example.moviesexplorer.data.models.MovieCategory

class Converters {
    @TypeConverter
    fun fromList(genre: List<Int>?): String? {
        return genre?.joinToString(",")
    }

    @TypeConverter
    fun toList(value: String?): List<Int>? {
        return value?.split(",")?.map { s -> s.toInt() }
    }

    @TypeConverter
    fun fromCategory(category: MovieCategory): String {
        return category.name
    }

    @TypeConverter
    fun toCategory(value: String): MovieCategory = enumValueOf(value)
}
