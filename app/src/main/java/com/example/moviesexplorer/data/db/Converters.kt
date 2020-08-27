package com.example.moviesexplorer.data.db

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromList(genre: List<Int>?): String? {
        return genre?.joinToString(",")
    }

    @TypeConverter
    fun toList(value: String?): List<Int>? {
        return value?.split(",")?.map { s -> s.toInt() }
    }
}
