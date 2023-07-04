package com.example.sportsapp.database.converters

import androidx.room.TypeConverter
import com.example.sportsapp.data.DayPlan
import com.example.sportsapp.data.MealPlanNutrients
import com.google.gson.Gson

class DayPlanConverter {

    @TypeConverter
    fun fromJson(json: String): DayPlan {
        return Gson().fromJson(json, DayPlan::class.java)
    }

    @TypeConverter
    fun toJson(dayPlan: DayPlan): String {
        return Gson().toJson(dayPlan)
    }
}