package com.example.sportsapp.repository

import com.example.sportsapp.database.MealDao
import com.example.sportsapp.entity.FavoriteMealEntity
import kotlinx.coroutines.flow.Flow

class MealRepository(private val dao: MealDao) {

    val mealList: Flow<List<FavoriteMealEntity>> = dao.getFavoriteMeals()

    suspend fun insert(mealEntity: FavoriteMealEntity): Long {
        return dao.insertFavoriteMeal(mealEntity)
    }

    suspend fun delete(mealEntity: FavoriteMealEntity): Int {
        return dao.deleteFavoriteMeal(mealEntity)
    }

    suspend fun exists(mealEntity: FavoriteMealEntity): Boolean {
        return dao.findMeal(mealEntity.id).isEmpty()
    }
}
