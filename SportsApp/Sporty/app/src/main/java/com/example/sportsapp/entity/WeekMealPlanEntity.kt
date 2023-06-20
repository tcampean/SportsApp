package com.example.sportsapp.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "WeekMealPlan", indices = [Index(value = ["name"], unique = true)])
data class WeekMealPlanEntity(
    val name: String,
    val monday: String,
    val tuesday: String,
    val wednesday: String,
    val thursday: String,
    val friday: String,
    val saturday: String,
    val sunday: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
