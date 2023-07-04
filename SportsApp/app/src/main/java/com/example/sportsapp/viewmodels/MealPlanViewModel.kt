package com.example.sportsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsapp.R
import com.example.sportsapp.api.SpoonacularAPI
import com.example.sportsapp.data.*
import com.example.sportsapp.database.MealPlanDao
import com.example.sportsapp.entity.DayMealPlanEntity
import com.example.sportsapp.entity.WeekMealPlanEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.await

class MealPlanViewModel : ViewModel() {

    private lateinit var dao: MealPlanDao

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

    private val _shouldDisplaySaveDialog = MutableStateFlow(false)
    val shouldDisplaySaveDialog = _shouldDisplaySaveDialog.asStateFlow()

    private val _saveName = MutableStateFlow("")
    val saveName = _saveName.asStateFlow()

    private var mealPlanLength = ""
    private var selectedDiet = ""
    private var generationLocked = false

    fun setDao(mealPlanDao: MealPlanDao) {
        dao = mealPlanDao
    }

    fun setDialogVisibility(visible: Boolean) {
        _shouldDisplaySaveDialog.value = visible
    }

    fun setSaveName(name: String) {
        _saveName.value = name
    }
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

    fun saveMealPlan() {
        if (isMealPlanLengthDay()) {
            saveDayMealPlan()
        } else {
            saveWeekMealPlan()
        }
    }

    private fun saveDayMealPlan() {
        viewModelScope.launch(Dispatchers.IO) {
            val recipeDetails = mutableListOf<RecipeDetailed>()
            val coroutineScope = CoroutineScope(Dispatchers.IO)

            val requests = _currentDayMealPlan.value.meals.map { meal ->
                coroutineScope.async {
                    val response =
                        SpoonacularAPI.retrofitService.getRecipeDetails(meal.id).execute()
                    if (response.isSuccessful) {
                        val recipeDetail = response.body()
                        if (recipeDetail != null) {
                            recipeDetails.add(recipeDetail)
                        }
                    }
                }
            }

            requests.awaitAll()

            dao.insertDayMealPlan(DayMealPlanEntity(_saveName.value, recipeDetails, _currentDayMealPlan.value.nutrients))
        }
    }

    private fun saveWeekMealPlan() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertWeekMealPlan(
                WeekMealPlanEntity(
                    _saveName.value,
                    _currentWeekMealPlan.value.week.monday,
                    _currentWeekMealPlan.value.week.tuesday,
                    _currentWeekMealPlan.value.week.wednesday,
                    _currentWeekMealPlan.value.week.thursday,
                    _currentWeekMealPlan.value.week.friday,
                    _currentWeekMealPlan.value.week.saturday,
                    _currentWeekMealPlan.value.week.sunday,
                ),
            )
            println("Saved in database")
        }
    }

    suspend fun checkIfExists(): Boolean {
        if (isMealPlanLengthDay()) {
            return dao.findDayMealPlan(_saveName.value).isEmpty()
        }
        return dao.findWeekMealPlan(_saveName.value).isEmpty()
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
