package com.example.sportsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsapp.data.DayPlan
import com.example.sportsapp.data.MealPlanNutrients
import com.example.sportsapp.entity.DayMealPlanEntity
import com.example.sportsapp.entity.WeekMealPlanEntity
import com.example.sportsapp.repository.MealPlanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteMealPlanViewModel : ViewModel() {

    private lateinit var repository: MealPlanRepository

    private val _savedDayMeals = MutableStateFlow(listOf<DayMealPlanEntity>())
    val savedDayMeals = _savedDayMeals.asStateFlow()

    private val _mealPlanLength = MutableStateFlow("")
    val mealPlanLength = _mealPlanLength.asStateFlow()

    private val _savedWeekMeals = MutableStateFlow(listOf<WeekMealPlanEntity>())
    val savedWeekMeals = _savedWeekMeals.asStateFlow()

    private val _currentDayMealPlan = MutableStateFlow(DayMealPlanEntity("", listOf(), MealPlanNutrients(0f, 0f, 0f, 0f)))
    val currentDayMealPlan = _currentDayMealPlan.asStateFlow()

    private val _currentWeekMealPlan = MutableStateFlow(
        WeekMealPlanEntity(
            "",
            DayPlan(listOf(), MealPlanNutrients(0f, 0f, 0f, 0f)),
            DayPlan(listOf(), MealPlanNutrients(0f, 0f, 0f, 0f)),
            DayPlan(listOf(), MealPlanNutrients(0f, 0f, 0f, 0f)),
            DayPlan(listOf(), MealPlanNutrients(0f, 0f, 0f, 0f)),
            DayPlan(listOf(), MealPlanNutrients(0f, 0f, 0f, 0f)),
            DayPlan(listOf(), MealPlanNutrients(0f, 0f, 0f, 0f)),
            DayPlan(listOf(), MealPlanNutrients(0f, 0f, 0f, 0f)),
        ),
    )
    val currentWeekMealPlan = _currentWeekMealPlan.asStateFlow()

    fun isMealPlanLengthDay(): Boolean {
        return mealPlanLength.value == "day"
    }

    fun setRepository(repo: MealPlanRepository) {
        repository = repo
    }

    fun getRepository(): MealPlanRepository {
        return repository
    }

    fun observeMealPlans() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.dayMealList.collect {
                _savedDayMeals.value = it
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            repository.weekMealList.collect {
                _savedWeekMeals.value = it
            }
        }
    }

    fun setCurrentDayMealPlan(meal: DayMealPlanEntity) {
        _currentDayMealPlan.value = meal
    }

    fun setCurrentWeekMealPlan(meal: WeekMealPlanEntity) {
        _currentWeekMealPlan.value = meal
    }

    fun setMealPlanLength(length: String) {
        _mealPlanLength.value = length
    }

    fun onDeleteClick(meal: DayMealPlanEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteDayPlan(meal)
        }
    }
}
