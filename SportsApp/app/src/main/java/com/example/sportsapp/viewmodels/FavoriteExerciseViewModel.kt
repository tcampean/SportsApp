package com.example.sportsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsapp.entity.ExerciseEntity
import com.example.sportsapp.repository.ExerciseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteExerciseViewModel : ViewModel() {
    private lateinit var repository: ExerciseRepository

    private val _refreshFlag = MutableStateFlow(false)
    val refreshFlag = _refreshFlag.asStateFlow()

    private val _favoriteExercises = MutableStateFlow(listOf<ExerciseEntity>())
    val favoriteExercises = _favoriteExercises.asStateFlow()

    fun setRepository(repo: ExerciseRepository) {
        repository = repo
    }
    fun observeFavoriteExercises() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.exerciseList.collect {
                _favoriteExercises.value = it
            }
        }
    }

    fun triggerRefreshFlag() {
        _refreshFlag.value = !_refreshFlag.value
    }

    fun onDeleteClick(exercise: ExerciseEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(exercise)
        }
    }
}