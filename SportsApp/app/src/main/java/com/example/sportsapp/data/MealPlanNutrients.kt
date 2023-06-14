package com.example.sportsapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MealPlanNutrients(
    val calories: Float,
    val carbohydrates: Float,
    val fat: Float,
    val protein: Float
) : Parcelable
