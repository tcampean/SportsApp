package com.example.sportsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsapp.api.ExerciseNinjaAPI
import com.example.sportsapp.data.Exercise
import com.example.sportsapp.database.ExerciseDao
import com.example.sportsapp.entity.ExerciseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.await

class ExerciseViewModel : ViewModel() {

    var offset = 0
    lateinit var dao: ExerciseDao

    private var isRequesting = false

    private val _difficulty = MutableStateFlow("")
    val difficulty = _difficulty.asStateFlow()

    private val _muscle = MutableStateFlow("")
    val muscle = _muscle.asStateFlow()

    private val _exerciseList = MutableStateFlow(listOf<Exercise>())
    val exerciseList = _exerciseList.asStateFlow()

    private val _shouldDisplayProgressBar = MutableStateFlow(false)
    val shouldDisplayProgressBar = _shouldDisplayProgressBar.asStateFlow()

    private val _currentExercise = MutableStateFlow(Exercise("", "", "", "", "", ""))
    val currentExercise = _currentExercise.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    fun getExercises() {
        if (!_shouldDisplayProgressBar.value && !isRequesting) {
            _shouldDisplayProgressBar.value = true
            viewModelScope.launch {
                val result = ExerciseNinjaAPI.retrofitService.getExercises(muscle = _muscle.value, offset = offset).await()
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
                val result = ExerciseNinjaAPI.retrofitService.getExercises(muscle = _muscle.value, offset = offset).await()
                _exerciseList.value = _exerciseList.value + result
                offset += 10
                isRequesting = false
            }
        }
    }
    fun setDifficulty(difficulty: String) {
        _difficulty.value = difficulty
    }

    fun checkIfFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = dao.findExercise(_currentExercise.value.name)
            _isFavorite.value = result.isNotEmpty()
        }
    }

    fun onSavedPressed() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!_isFavorite.value) {
                dao.insert(
                    ExerciseEntity(
                        _currentExercise.value.name,
                        _currentExercise.value.type,
                        _currentExercise.value.muscle,
                        _currentExercise.value.equipment,
                        _currentExercise.value.difficulty,
                        _currentExercise.value.instructions,
                    ),
                )
                _isFavorite.value = true
            } else {
                dao.delete(
                        _currentExercise.value.name,
                    )
                println("deleted")
                _isFavorite.value = false
            }
        }
    }

    fun setCurrentExercise(exercise: Exercise) {
        _currentExercise.value = exercise
    }

    fun setMuscle(muscle: String) {
        _muscle.value = muscle
    }
}
