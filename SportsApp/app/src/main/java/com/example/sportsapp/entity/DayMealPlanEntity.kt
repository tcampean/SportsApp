package com.example.sportsapp.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.sportsapp.data.Meal
import com.example.sportsapp.data.MealPlanNutrients
import com.example.sportsapp.data.RecipeDetailed
import com.example.sportsapp.database.converters.MealPlanNutrientsConverter
import com.example.sportsapp.database.converters.RecipeDetailedListConverter

@Entity(tableName = "DayMealPlan", indices = [Index(value = ["name"], unique = true)])
@TypeConverters(RecipeDetailedListConverter::class, MealPlanNutrientsConverter::class)
data class DayMealPlanEntity(
    val name: String,
    val meals: List<RecipeDetailed>,
    val nutrients: MealPlanNutrients,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
