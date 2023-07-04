package com.example.sportsapp.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.sportsapp.data.DayPlan
import com.example.sportsapp.database.converters.DayPlanConverter

@Entity(tableName = "WeekMealPlan", indices = [Index(value = ["name"], unique = true)])
@TypeConverters(DayPlanConverter::class)
data class WeekMealPlanEntity(
    val name: String,
    val monday: DayPlan,
    val tuesday: DayPlan,
    val wednesday: DayPlan,
    val thursday: DayPlan,
    val friday: DayPlan,
    val saturday: DayPlan,
    val sunday: DayPlan,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
