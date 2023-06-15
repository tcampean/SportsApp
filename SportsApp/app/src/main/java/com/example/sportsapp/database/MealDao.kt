package com.example.sportsapp.database

import androidx.room.*
import com.example.sportsapp.entity.FavoriteMealEntity
import kotlinx.coroutines.flow.StateFlow

@Dao
interface MealDao {

    @Query("SELECT * FROM FavoriteMeal")
    fun getFavoriteMeal(): StateFlow<List<FavoriteMealEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertFavoriteMeal(mealEntity: FavoriteMealEntity): Long

    @Delete
    fun deleteProduct(mealEntity: FavoriteMealEntity): Int


}