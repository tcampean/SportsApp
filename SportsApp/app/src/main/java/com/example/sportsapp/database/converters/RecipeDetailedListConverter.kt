package com.example.sportsapp.database.converters

import androidx.room.TypeConverter
import com.example.sportsapp.data.RecipeDetailed
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeDetailedListConverter {

    @TypeConverter
    fun fromJson(json: String): List<RecipeDetailed> {
        return Gson().fromJson(json, object : TypeToken<List<RecipeDetailed>>() {}.type)
    }

    @TypeConverter
    fun toJson(meals: List<RecipeDetailed>): String {
        return Gson().toJson(meals)
    }
}
