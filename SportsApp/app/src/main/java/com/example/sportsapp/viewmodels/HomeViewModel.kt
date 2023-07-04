package com.example.sportsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportsapp.api.MainAPI
import com.example.sportsapp.data.UserData
import com.example.sportsapp.database.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.await
import retrofit2.awaitResponse
import java.lang.Exception

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

    private val _shouldDisplayWeightDialog = MutableStateFlow(false)
    val shouldDisplayWeightDialog = _shouldDisplayWeightDialog.asStateFlow()

    private val _weightDialog = MutableStateFlow(-1)
    val weightDialog = _weightDialog.asStateFlow()

    private val _weight = MutableStateFlow(UserData.user.weight)
    val weight = _weight.asStateFlow()


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

    fun setWeightDialogDisplayFlag(flag: Boolean) {
        _shouldDisplayWeightDialog.value = flag
    }

    fun setDialogWeight(weight: Int) {
        _weightDialog.value = weight
    }

    fun setWeight(weight: Int, userDao: UserDao) {
        _weight.value = weight
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = MainAPI.retrofitService.updateWeight(UserData.user.username, _weight.value).awaitResponse()
                println(result.isSuccessful)

            } catch (e: Exception) {
                println(e.message)
            }

            userDao.updateWeight(_weight.value)
        }
    }
}
