package com.example.sportsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsapp.database.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    init {
        println("VM RESTARTED")
    }

    private val _currentCalories = MutableStateFlow(-1)
    val currentCalories = _currentCalories.asStateFlow()

    private val _currentCaloriesString = MutableStateFlow("")
    val currentCaloriesString = _currentCaloriesString.asStateFlow()

    private val _remainingCalories = MutableStateFlow(100)
    val remainingCalories = _remainingCalories.asStateFlow()

    private val _remainingCaloriesString = MutableStateFlow("")
    val remainingCaloriesString = _remainingCaloriesString.asStateFlow()

    private val _refreshFlag = MutableStateFlow(true)
    val refreshFlag = _refreshFlag.asStateFlow()

    fun setCaloricValues(currentCalories: Int, total: Int) {
        _currentCalories.value = currentCalories
        _remainingCalories.value = total - _currentCalories.value
        _currentCaloriesString.value = _currentCalories.value.toString()
        _remainingCaloriesString.value = _remainingCalories.value.toString()
    }

    fun triggerReset() {
        _refreshFlag.value = !_refreshFlag.value
    }

    fun signOut(userDao: UserDao) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.clearUser()
        }
    }
}
