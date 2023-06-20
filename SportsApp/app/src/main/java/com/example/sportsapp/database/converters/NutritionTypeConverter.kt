package com.example.sportsapp.database.converters

import androidx.room.TypeConverter
import com.example.sportsapp.data.Nutrition
import com.google.gson.Gson

class NutritionTypeConverter {
    @TypeConverter
    fun fromJson(json: String): Nutrition {
        return Gson().fromJson(json, Nutrition::class.java)
    }

    @TypeConverter
    fun toJson(nutrition: Nutrition): String {
        return Gson().toJson(nutrition)
    }
}