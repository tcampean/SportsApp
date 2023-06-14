package com.example.sportsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsapp.R
import com.example.sportsapp.api.SpoonacularAPI
import com.example.sportsapp.data.DayPlan
import com.example.sportsapp.data.MealPlanCategory
import com.example.sportsapp.data.MealPlanNutrients
import com.example.sportsapp.data.Week
import com.example.sportsapp.data.WeekPlan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.await

class MealPlanViewModel : ViewModel() {
    val mealPlanCategories = listOf(
        MealPlanCategory("Gluten Free", R.drawable.gluten_free_card_image),
        MealPlanCategory("Ketogenic", R.drawable.keto_card_image),
        MealPlanCategory("Vegetarian", R.drawable.vegetarian_card_image),
        MealPlanCategory("Lacto-Vegetarian", R.drawable.lacto_vegetarian_card_image),
        MealPlanCategory("Ovo-Vegetarian", R.drawable.ovo_vegetarian_card_image),
        MealPlanCategory("Vegan", R.drawable.vegan_card_image),
        MealPlanCategory("Pescetarian", R.drawable.pescetarian_card_image),
        MealPlanCategory("Paleo", R.drawable.paleo_card_image),
    )

    private val _currentDayMealPlan = MutableStateFlow(DayPlan(listOf(), MealPlanNutrients(0f, 0f, 0f, 0f)))
    val currentDayMealPlan = _currentDayMealPlan.asStateFlow()

    private val _currentWeekMealPlan = MutableStateFlow(
        WeekPlan(
            Week(
                DayPlan(listOf(), MealPlanNutrients(0f, 0f, 0f, 0f)),
                DayPlan(listOf(), MealPlanNutrients(0f, 0f, 0f, 0f)),
                DayPlan(listOf(), MealPlanNutrients(0f, 0f, 0f, 0f)),
                DayPlan(listOf(), MealPlanNutrients(0f, 0f, 0f, 0f)),
                DayPlan(listOf(), MealPlanNutrients(0f, 0f, 0f, 0f)),
                DayPlan(listOf(), MealPlanNutrients(0f, 0f, 0f, 0f)),
                DayPlan(listOf(), MealPlanNutrients(0f, 0f, 0f, 0f)),
            ),
        ),
    )
    val currentWeekMealPlan = _currentWeekMealPlan.asStateFlow()

    private val _shouldDisplayProgressBar = MutableStateFlow(false)
    val shouldDisplayProgressBar = _shouldDisplayProgressBar.asStateFlow()

    private var mealPlanLength = ""
    private var selectedDiet = ""
    private var generationLocked = false

    fun setMealPlanLength(length: String) {
        mealPlanLength = length
    }

    fun setSelectedDiet(diet: String) {
        selectedDiet = diet
    }

    fun resetGenerationLock() {
        generationLocked = false
    }

    fun isMealPlanLengthDay(): Boolean {
        return mealPlanLength == "day"
    }

    fun generateNewMealPlan() {
        if (!_shouldDisplayProgressBar.value && !generationLocked) {
            generationLocked = true
            _shouldDisplayProgressBar.value = true
            viewModelScope.launch {
                if (mealPlanLength == "day") {
                    val result =
                        SpoonacularAPI.retrofitService.generateDayMeal(selectedDiet).await()
                    _currentDayMealPlan.value = result
                } else {
                    val result = SpoonacularAPI.retrofitService.generateWeekMeal(selectedDiet).await()
                    println(result)
                    _currentWeekMealPlan.value = result
                    println(_currentWeekMealPlan.value)
                }
                _shouldDisplayProgressBar.value = false
            }
        }
    }
}
