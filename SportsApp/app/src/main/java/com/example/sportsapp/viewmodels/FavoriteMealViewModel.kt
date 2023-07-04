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

    private val _refreshFlag = MutableStateFlow(false)
    val refreshFlag = _refreshFlag.asStateFlow()

    private val _favoriteMeals = MutableStateFlow(listOf<FavoriteMealEntity>())
    val favoriteMeals = _favoriteMeals.asStateFlow()
    fun setRepository(repo: MealRepository) {
        repository = repo
    }
    fun observeFavoriteMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.mealList.collect {
                _favoriteMeals.value = it
            }
        }
    }

    fun triggerRefreshFlag() {
        _refreshFlag.value = !_refreshFlag.value
    }

    fun onDeleteClick(meal: FavoriteMealEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(meal)
            _favoriteMeals.value = _favoriteMeals.value.filter { it != meal }
        }
    }
}
