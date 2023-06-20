package com.example.sportsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsapp.entity.FavoriteMealEntity
import com.example.sportsapp.repository.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteMealViewModel : ViewModel() {

    private lateinit var repository: MealRepository

    private val _favoriteMeals = MutableStateFlow(listOf<FavoriteMealEntity>())
    val favoriteMeals = _favoriteMeals.asStateFlow()

    fun setRepository(repo: MealRepository) {
        repository = repo
    }

    fun getRepository(): MealRepository {
        return repository
    }

    fun observeFavoriteMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.mealList.collect {
                _favoriteMeals.value = it
            }
        }
    }

    fun onDeleteClick(meal: FavoriteMealEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(meal)
        }
    }
}
