package com.example.sportsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsapp.api.SpoonacularAPI
import com.example.sportsapp.data.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.await

class SearchFoodViewModel : ViewModel() {

    var offset = 0
    private var isRequesting = false
    private var query = ""

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _recipeList = MutableStateFlow(listOf<Recipe>())
    val recipeList = _recipeList.asStateFlow()

    private val _shouldDisplayProgressBar = MutableStateFlow(false)
    val shouldDisplayProgressBar = _shouldDisplayProgressBar.asStateFlow()

    var firstTime = true

    fun setSearchText(text: String) {
        _searchText.value = text
    }

    private fun requestData(onRequestFailed: () -> Unit = {}) {
        if (!isRequesting) {
            isRequesting = true
            _shouldDisplayProgressBar.value = true
            viewModelScope.launch(Dispatchers.IO) {
                offset = 0
                try {
                    val result = SpoonacularAPI.retrofitService.getRecipes(query, offset).await()
                    _recipeList.value = result.results
                    offset += 10
                } catch (e: java.lang.Exception) {
                    launch(Dispatchers.Main){
                        onRequestFailed()
                    }
                }
                isRequesting = false
                _shouldDisplayProgressBar.value = false
                firstTime = false
            }
        }
    }

    fun requestDataAppend(onRequestFailed: () -> Unit) {
        if (!isRequesting) {
            isRequesting = true
            _shouldDisplayProgressBar.value = true
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val result = SpoonacularAPI.retrofitService.getRecipes(query, offset).await()
                    _recipeList.value = _recipeList.value + result.results
                    offset += 10
                } catch (e: java.lang.Exception) {
                    launch(Dispatchers.Main) {
                        onRequestFailed()
                    }
                }
                delay(1000)
                isRequesting = false
                _shouldDisplayProgressBar.value = false
            }
        }
    }

    fun searchRecipe(onRequestFailed: () -> Unit) {
        query = _searchText.value
        requestData(onRequestFailed = { onRequestFailed() })
    }
}
