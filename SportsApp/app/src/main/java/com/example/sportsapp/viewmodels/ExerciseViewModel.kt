package com.example.sportsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsapp.api.ExerciseNinjaAPI
import com.example.sportsapp.api.SpoonacularAPI
import com.example.sportsapp.data.Exercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.await

class ExerciseViewModel : ViewModel() {

    var offset = 0

    private var isRequesting = false

    private val _difficulty = MutableStateFlow("")
    val difficulty = _difficulty.asStateFlow()

    private val _muscle = MutableStateFlow("")
    val muscle = _muscle.asStateFlow()

    private val _exerciseList = MutableStateFlow(listOf<Exercise>())
    val exerciseList = _exerciseList.asStateFlow()

    private val _shouldDisplayProgressBar = MutableStateFlow(false)
    val shouldDisplayProgressBar = _shouldDisplayProgressBar.asStateFlow()

    fun getExercises() {
        if (!_shouldDisplayProgressBar.value && !isRequesting) {
            _shouldDisplayProgressBar.value = true
            viewModelScope.launch {
                val result = ExerciseNinjaAPI.retrofitService.getExercises(muscle = _muscle.value, difficulty = _difficulty.value, offset = offset).await()
                _exerciseList.value = result
                offset += 10
                _shouldDisplayProgressBar.value = false
            }
        }
    }

    fun requestDataAppend() {
        if (!isRequesting) {
            isRequesting = true
            viewModelScope.launch(Dispatchers.IO) {
                val result = ExerciseNinjaAPI.retrofitService.getExercises(muscle = _muscle.value, difficulty = _difficulty.value, offset = offset).await()
                _exerciseList.value = _exerciseList.value + result
                offset += 10
                isRequesting = false
            }
        }
    }
    fun setDifficulty(difficulty: String) {
        _difficulty.value = difficulty
    }

    fun setMuscle(muscle: String) {
        _muscle.value = muscle
    }
}
