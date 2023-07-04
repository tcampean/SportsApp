package com.example.sportsapp.viewmodels

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsapp.api.SpoonacularAPI
import com.example.sportsapp.data.Ingredient
import com.example.sportsapp.data.Nutrients
import com.example.sportsapp.data.Nutrition
import com.example.sportsapp.data.RecipeDetailed
import com.example.sportsapp.database.MealDao
import com.example.sportsapp.entity.FavoriteMealEntity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.await
import java.net.SocketTimeoutException

class FoodDetailsViewModel : ViewModel() {

    private var isRequesting = false
    private var recipeID = -1
    private var isLocal = false
    private lateinit var dao: MealDao
    private var recipeUrl = ""
    private lateinit var nutrition: Nutrition

    private val _image = MutableStateFlow<ImageBitmap?>(ImageBitmap(1, 1))
    val image = _image.asStateFlow()

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _servings = MutableStateFlow(0)
    val servings = _servings.asStateFlow()

    private val _readyInMinutes = MutableStateFlow(0)
    val readyInMinutes = _readyInMinutes.asStateFlow()

    private val _extendedIngredients = MutableStateFlow(listOf<Ingredient>())
    val extendedIngredients = _extendedIngredients.asStateFlow()

    private val _summary = MutableStateFlow("")
    val summary = _summary.asStateFlow()

    private val _calories = MutableStateFlow(0F)
    val calories = _calories.asStateFlow()

    private val _protein = MutableStateFlow(0F)
    val protein = _protein.asStateFlow()

    private val _fat = MutableStateFlow(0F)
    val fat = _fat.asStateFlow()

    private val _carbs = MutableStateFlow(0F)
    val carbs = _carbs.asStateFlow()

    private val _shouldDisplayProgressBar = MutableStateFlow(false)
    val shouldDisplayProgressBar = _shouldDisplayProgressBar.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    fun setRecipeId(id: Int) {
        recipeID = id
    }

    fun setDao(d: MealDao) {
        dao = d
    }

    fun checkIfFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = dao.findMeal(recipeID)
            if (result.isNotEmpty()) {
                _isFavorite.value = true
            }
        }
    }

    fun setCurrentRecipe(recipe: RecipeDetailed) {
        recipeID = recipe.id
        recipeUrl = recipe.image
        _title.value = recipe.title
        _servings.value = recipe.servings
        _readyInMinutes.value = recipe.readyInMinutes
        _extendedIngredients.value = recipe.extendedIngredients
        _summary.value = recipe.summary
        nutrition = recipe.nutrition
        setNutritionalValues(recipe.nutrition.nutrients)
        isLocal = true
    }

    fun onSaveButtonPressed() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentMeal = FavoriteMealEntity(
                _title.value,
                recipeUrl,
                ".jpg",
                _servings.value,
                _readyInMinutes.value,
                _extendedIngredients.value,
                _summary.value,
                nutrition,
                recipeID,
            )
            if (_isFavorite.value) {
                dao.deleteFavoriteMeal(currentMeal)
            } else {
                dao.insertFavoriteMeal(currentMeal)
            }
            _isFavorite.value = !_isFavorite.value
        }
    }

    fun requestRecipeDetails() {
        println("GOT INTO EXCEPTION")
        if (!isRequesting && !isLocal) {
            println("doing the request")
            _shouldDisplayProgressBar.value = true
            isRequesting = true
            viewModelScope.launch(Dispatchers.IO) {
                val result = SpoonacularAPI.retrofitService.getRecipeDetails(recipeID).await()
                try {
                    val bitmap = Picasso.get().load(result.image).get()
                    _image.value = bitmap.asImageBitmap()
                } catch (e: SocketTimeoutException) {
                    _image.value = null
                }
                recipeUrl = result.image
                _title.value = result.title
                _servings.value = result.servings
                _readyInMinutes.value = result.readyInMinutes
                _extendedIngredients.value = result.extendedIngredients
                _summary.value = result.summary
                nutrition = result.nutrition
                setNutritionalValues(result.nutrition.nutrients)
                isRequesting = false
                _shouldDisplayProgressBar.value = false
            }
        } else if (isLocal) {
            viewModelScope.launch(Dispatchers.IO) {
                _shouldDisplayProgressBar.value = true
                println("GOT INTO EXCEPTION 2")
                println("GOT INTO EXCEPTION 3")
                try {
                    val result = SpoonacularAPI.retrofitService.getRecipeDetails(recipeID).await()
                    val bitmap = Picasso.get().load(result.image).get()
                    _image.value = bitmap.asImageBitmap()
                } catch (e: Exception) {
                    println("GOT INTO EXCEPTION" + e.message)
                    _image.value = null
                }
                _shouldDisplayProgressBar.value = false
            }
        }
    }

    private fun setNutritionalValues(nutrients: List<Nutrients>) {
        var valueAmount = 4
        for (nutrient in nutrients) {
            when (nutrient.name) {
                "Calories" -> {
                    _calories.value = nutrient.amount
                    valueAmount -= 1
                }

                "Carbohydrates" -> {
                    _carbs.value = nutrient.amount
                    valueAmount -= 1
                }

                "Fat" -> {
                    _fat.value = nutrient.amount
                    valueAmount -= 1
                }

                "Protein" -> {
                    _protein.value = nutrient.amount
                    valueAmount -= 1
                }
            }
            if (valueAmount == 0) {
                break
            }
        }
    }
}
