package com.example.sportsapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DayPlan(
    val meals: List<Meal>,
    val nutrients: MealPlanNutrients
) : Parcelable
