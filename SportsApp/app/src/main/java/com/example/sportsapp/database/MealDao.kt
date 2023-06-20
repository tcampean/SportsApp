package com.example.sportsapp.database

import androidx.room.*
import com.example.sportsapp.entity.FavoriteMealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Query("SELECT * FROM FavoriteMeal")
    fun getFavoriteMeals(): Flow<List<FavoriteMealEntity>>

    @Query("SELECT * FROM FavoriteMeal WHERE id = :mealId")
    fun findMeal(mealId: Int): List<FavoriteMealEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertFavoriteMeal(mealEntity: FavoriteMealEntity): Long

    @Delete
    fun deleteFavoriteMeal(mealEntity: FavoriteMealEntity): Int


}