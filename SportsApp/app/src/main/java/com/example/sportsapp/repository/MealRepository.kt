package com.example.sportsapp.repository

import com.example.sportsapp.database.MealDao
import com.example.sportsapp.entity.FavoriteMealEntity
import kotlinx.coroutines.flow.StateFlow

class MealRepository(private val dao: MealDao) {

    val productList: StateFlow<List<FavoriteMealEntity>> = dao.getFavoriteMeal()

    suspend fun insert(mealEntity: FavoriteMealEntity): Long {
        return dao.insertFavoriteMeal(mealEntity)
    }

    suspend fun delete(mealEntity: FavoriteMealEntity): Int {
        return dao.deleteProduct(mealEntity)
    }

}