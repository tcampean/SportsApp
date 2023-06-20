package com.example.sportsapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.sportsapp.data.Ingredient
import com.example.sportsapp.data.Nutrition
import com.example.sportsapp.database.converters.IngredientTypeConverter
import com.example.sportsapp.database.converters.NutritionTypeConverter

@Entity(tableName = "FavoriteMeal")
@TypeConverters(IngredientTypeConverter::class, NutritionTypeConverter::class)
data class FavoriteMealEntity(
    val title: String,
    val image: String,
    val imageType: String,
    val servings: Int,
    val readyInMinutes: Int,
    val extendedIngredients: List<Ingredient>,
    val summary: String,
    val nutrition: Nutrition,
    @PrimaryKey val id: Int = 0,
)
