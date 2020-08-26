package com.example.moviesexplorer.data.db

import androidx.room.TypeConverter
import com.example.moviesexplorer.data.models.Genre
import com.example.moviesexplorer.data.models.ProductionCompany
import com.example.moviesexplorer.data.models.SpokenLanguage
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun fromProductionCompany(productionCompany: ProductionCompany): String {
        return Gson().toJson(productionCompany)
    }

    @TypeConverter
    fun toProductionCompany(value: String): ProductionCompany {
        return Gson().fromJson(value, ProductionCompany::class.java)
    }

    @TypeConverter
    fun fromSpokenLanguage(spokenLanguage: SpokenLanguage): String {
        return Gson().toJson(spokenLanguage)
    }

    @TypeConverter
    fun toSpokenLanguage(value: String): SpokenLanguage {
        return Gson().fromJson(value, SpokenLanguage::class.java)
    }

    @TypeConverter
    fun fromGenre(genre: Genre): String {
        return Gson().toJson(genre)
    }

    @TypeConverter
    fun toGenre(value: String): Genre {
        return Gson().fromJson(value, Genre::class.java)
    }
}