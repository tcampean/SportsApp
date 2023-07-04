package com.example.sportsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsapp.api.ExerciseNinjaAPI
import com.example.sportsapp.data.Exercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.await

class SearchExercisesViewModel() : ViewModel() {

    var offset = 0
    private var isRequesting = false
    private var query = ""

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _exerciseList = MutableStateFlow(listOf<Exercise>())
    val exerciseList = _exerciseList.asStateFlow()

    fun setSearchText(text: String) {
        _searchText.value = text
    }

    private fun requestData() {
        if (!isRequesting) {
            isRequesting = true
            viewModelScope.launch(Dispatchers.IO) {
                offset = 0
                val result = ExerciseNinjaAPI.retrofitService.getExercisesByName(name = query, offset = offset).await()
                _exerciseList.value = result
                offset += 10
                isRequesting = false
            }
        }
    }

    fun requestDataAppend() {
        if (!isRequesting) {
            isRequesting = true
            viewModelScope.launch(Dispatchers.IO) {
                val result = ExerciseNinjaAPI.retrofitService.getExercisesByName(name = query, offset = offset).await()
                _exerciseList.value = _exerciseList.value + result
                offset += 10
                delay(1000)
                isRequesting = false
            }
        }
    }

    fun searchRecipe() {
        query = _searchText.value
        requestData()
    }
}