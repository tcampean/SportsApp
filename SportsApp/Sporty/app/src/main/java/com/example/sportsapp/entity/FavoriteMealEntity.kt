package com.example.sportsapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sportsapp.data.Ingredient
import com.example.sportsapp.data.Nutrition

@Entity(tableName = "FavoriteMeal")
data class FavoriteMealEntity(
    val title: String,
    val image: String,
    val imageType: String,
    val servings: Int,
    val readyInMinutes: Int,
    val extendedIngredients: Int,
    val summary: String,
    val nutrition: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
