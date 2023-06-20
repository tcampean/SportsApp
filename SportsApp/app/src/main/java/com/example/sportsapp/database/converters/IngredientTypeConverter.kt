package com.example.sportsapp.database.converters

import androidx.room.TypeConverter
import com.example.sportsapp.data.Ingredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IngredientTypeConverter {

    @TypeConverter
    fun fromJson(json: String): List<Ingredient> {
        val typeToken = object : TypeToken<List<Ingredient>>() {}.type
        return Gson().fromJson(json, typeToken)
    }

    @TypeConverter
    fun toJson(ingredients: List<Ingredient>): String {
        return Gson().toJson(ingredients)
    }
}
