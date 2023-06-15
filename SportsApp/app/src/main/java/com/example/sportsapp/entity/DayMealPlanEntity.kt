package com.example.sportsapp.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.sportsapp.data.Meal
import com.example.sportsapp.data.MealPlanNutrients

@Entity(tableName = "DayMealPlan", indices = [Index(value = ["name"], unique = true)])
data class DayMealPlanEntity(
    val name: String,
    val meals: List<Meal>,
    val nutrients: MealPlanNutrients,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
