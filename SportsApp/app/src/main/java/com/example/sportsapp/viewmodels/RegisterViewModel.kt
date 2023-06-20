package com.example.sportsapp.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel : ViewModel() {

    val goalList = ArrayList(
        listOf(
            "Lose Weight",
            "Maintain Weight",
            "Gain Weight",
            "Gain Muscle",
        ),
    )
    val activityList = ArrayList(
        listOf(
            "Sedentary",
            "Lightly Active",
            "Active",
            "Very Active",
        ),
    )
    val activityDescriptionList = ArrayList(
        listOf(
            "Spend most of the day sitting with no sport",
            "Light activities during the day",
            "Spend a good part of the day doing physical activities",
            "Spend a good part of the day doing heavy physical activities",
        ),
    )

    private val _selectedGoal = MutableStateFlow(-1)
    val selectedGoal = _selectedGoal.asStateFlow()

    private val _selectedActivity = MutableStateFlow(-1)
    val selectedActivity = _selectedActivity.asStateFlow()

    private val _selectedGender = MutableStateFlow("")
    val selectedGender = _selectedGender.asStateFlow()

    private val _isWeightCorrect = MutableStateFlow(true)
    val isWeightCorrect = _isWeightCorrect.asStateFlow()

    private val _isHeightCorrect = MutableStateFlow(true)
    val isHeightCorrect = _isHeightCorrect.asStateFlow()

    private val _weightInput = MutableStateFlow("")
    val weightInput = _weightInput.asStateFlow()

    private val _heightInput = MutableStateFlow("")
    val heightInput = _heightInput.asStateFlow()

    private val _usernameInput = MutableStateFlow("")
    val usernameInput = _usernameInput.asStateFlow()

    private val _passwordInput = MutableStateFlow("")
    val passwordInput = _passwordInput.asStateFlow()

    private val _ageInput = MutableStateFlow("")
    val ageInput = _ageInput.asStateFlow()

    fun setSelectedGoal(index: Int) {
        _selectedGoal.value = index
    }

    fun setSelectedActivity(index: Int) {
        _selectedActivity.value = index
    }

    fun setSelectedGender(gender: String) {
        _selectedGender.value = gender
    }

    fun onWeightInputChange(weight: String) {
        _weightInput.value = weight
    }

    fun onHeightInputChange(height: String) {
        _heightInput.value = height
    }

    fun onAgeInputChange(age: String) {
        _ageInput.value = age
    }

    fun onUsernameInputChange(username: String) {
        _usernameInput.value = username
    }

    fun onPasswordInputChange(password: String) {
        _passwordInput.value = password
    }
}
