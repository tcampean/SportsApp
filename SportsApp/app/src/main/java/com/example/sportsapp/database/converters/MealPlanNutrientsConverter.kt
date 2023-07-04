package com.example.sportsapp.database.converters

import androidx.room.TypeConverter
import com.example.sportsapp.data.MealPlanNutrients
import com.google.gson.Gson

class MealPlanNutrientsConverter {
    @TypeConverter
    fun fromJson(json: String): MealPlanNutrients {
        return Gson().fromJson(json, MealPlanNutrients::class.java)
    }

    @TypeConverter
    fun toJson(nutrients: MealPlanNutrients): String {
        return Gson().toJson(nutrients)
    }
}