package com.example.sportsapp.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExerciseViewModel : ViewModel() {

    private val _difficulty = MutableStateFlow("")
    val difficulty = _difficulty.asStateFlow()

    private val _muscle = MutableStateFlow("")
    val muscle = _muscle.asStateFlow()

    fun setDifficulty(difficulty: String) {
        _difficulty.value = difficulty
    }

    fun setMuscle(muscle: String) {
        _muscle.value = muscle
    }
}
