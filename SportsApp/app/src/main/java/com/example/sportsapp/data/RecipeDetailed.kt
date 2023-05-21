package com.example.sportsapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeDetailed(
    val id: Int,
    val title: String,
    val image: String,
    val imageType: String,
    val servings: Int,
    val readyInMinutes: Int,
    val extendedIngredients: List<Ingredient>,
    val summary: String,
    val nutrition: Nutrition,
) : Parcelable
