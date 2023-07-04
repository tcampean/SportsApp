package com.example.sportsapp.repository

import com.example.sportsapp.database.MealPlanDao
import com.example.sportsapp.entity.DayMealPlanEntity
import com.example.sportsapp.entity.WeekMealPlanEntity
import kotlinx.coroutines.flow.Flow

class MealPlanRepository(private val dao: MealPlanDao) {

    val dayMealList: Flow<List<DayMealPlanEntity>> = dao.getSavedDayPlans()
    val weekMealList: Flow<List<WeekMealPlanEntity>> = dao.getSavedWeekPlans()
    suspend fun insertDayPlan(mealEntity: DayMealPlanEntity): Long {
        return dao.insertDayMealPlan(mealEntity)
    }

    suspend fun insertWeekPlan(mealEntity: WeekMealPlanEntity): Long {
        return dao.insertWeekMealPlan(mealEntity)
    }

    suspend fun deleteDayPlan(mealEntity: DayMealPlanEntity): Int {
        return dao.deleteDayMealPlan(mealEntity)
    }

    suspend fun deleteWeekPlan(mealEntity: WeekMealPlanEntity): Int {
        return dao.deleteWeekMealPlan(mealEntity)
    }

    suspend fun existsDayPlan(mealEntity: DayMealPlanEntity): Boolean {
        return dao.findDayMealPlan(mealEntity.name).isEmpty()
    }

    suspend fun existsWeekPlan(mealEntity: WeekMealPlanEntity): Boolean {
        return dao.findWeekMealPlan(mealEntity.name).isEmpty()
    }
}