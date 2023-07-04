package com.example.sportsapp.database

import androidx.room.*
import com.example.sportsapp.entity.DayMealPlanEntity
import com.example.sportsapp.entity.WeekMealPlanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealPlanDao {

    @Query("SELECT * FROM DayMealPlan")
    fun getSavedDayPlans(): Flow<List<DayMealPlanEntity>>

    @Query("SELECT * FROM WeekMealPlan")
    fun getSavedWeekPlans(): Flow<List<WeekMealPlanEntity>>

    @Query("SELECT * FROM DayMealPlan WHERE name = :mealPlanName")
    fun findDayMealPlan(mealPlanName: String): List<DayMealPlanEntity>

    @Query("SELECT * FROM WeekMealPlan WHERE name = :mealPlanName")
    fun findWeekMealPlan(mealPlanName: String): List<WeekMealPlanEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertDayMealPlan(mealEntity: DayMealPlanEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertWeekMealPlan(mealEntity: WeekMealPlanEntity): Long

    @Delete
    fun deleteWeekMealPlan(mealEntity: WeekMealPlanEntity): Int

    @Delete
    fun deleteDayMealPlan(mealEntity: DayMealPlanEntity): Int
}
