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

    private val _selectedGender = MutableStateFlow(-1)
    val selectedGender = _selectedGender.asStateFlow()

    fun setSelectedGoal(index: Int) {
        _selectedGoal.value = index
    }

    fun setSelectedActivity(index: Int) {
        _selectedActivity.value = index
    }
}
