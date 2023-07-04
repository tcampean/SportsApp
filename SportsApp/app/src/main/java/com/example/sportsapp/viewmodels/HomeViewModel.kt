package com.example.sportsapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsapp.api.MainAPI
import com.example.sportsapp.data.UserData
import com.example.sportsapp.database.DiaryDao
import com.example.sportsapp.database.UserDao
import com.example.sportsapp.entity.DiaryEntryEntity
import com.example.sportsapp.repository.DiaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse
import java.lang.Exception
import java.util.Calendar
class HomeViewModel : ViewModel() {

    private lateinit var repository: DiaryRepository

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

    private val _shouldDisplayFoodDialog = MutableStateFlow(false)
    val shouldDisplayFoodDialog = _shouldDisplayFoodDialog.asStateFlow()

    private val _shouldDisplayActivityDialog = MutableStateFlow(false)
    val shouldDisplayActivityDialog = _shouldDisplayActivityDialog.asStateFlow()

    private val _weightDialog = MutableStateFlow(-1)
    val weightDialog = _weightDialog.asStateFlow()

    private val _weight = MutableStateFlow(UserData.user.weight)
    val weight = _weight.asStateFlow()

    private val _label = MutableStateFlow("")
    val label = _label.asStateFlow()

    private val _calories = MutableStateFlow("")
    val calories = _calories.asStateFlow()

    private val _activities = MutableStateFlow(listOf<DiaryEntryEntity>())
    val activities = _activities.asStateFlow()

    private val _foods = MutableStateFlow(listOf<DiaryEntryEntity>())
    val foods = _foods.asStateFlow()

    private val _resetFlag = MutableStateFlow(false)
    val resetFlag = _resetFlag.asStateFlow()

    fun setRepository(repository: DiaryRepository) {
        this.repository = repository
    }

    fun trigger() {
        _resetFlag.value = !_resetFlag.value
    }

    fun observeMealPlans() {
        viewModelScope.launch(Dispatchers.Main) {
            repository.activityEntries.collect {
                _activities.value = it
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            repository.foodEntries.collect {
                _foods.value = it
            }
        }
    }

    fun setCaloricValues(currentCalories: Int, total: Int) {
        _currentCalories.value = currentCalories
        _remainingCalories.value = total - _currentCalories.value
        _currentCaloriesString.value = _currentCalories.value.toString()
        _remainingCaloriesString.value = _remainingCalories.value.toString()
    }

    fun updateCaloricValues() {
        viewModelScope.launch {
            delay(500)
            var sum = 0
            for (entity in _foods.value) {
                sum += entity.calories
            }

            for (entity in _activities.value) {
                sum += entity.calories
            }
            _currentCalories.value = sum
            _remainingCalories.value = UserData.requiredCalories - _currentCalories.value
            _currentCaloriesString.value = _currentCalories.value.toString()
            _remainingCaloriesString.value = _remainingCalories.value.toString()

        }
    }

    fun signOut(userDao: UserDao) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.clearUser()
        }
    }

    fun setWeightDialogDisplayFlag(flag: Boolean) {
        _shouldDisplayWeightDialog.value = flag
    }

    fun setFoodDialogDisplayFlag(flag: Boolean) {
        _shouldDisplayFoodDialog.value = flag
    }

    fun setActivityDialogDisplayFlag(flag: Boolean) {
        _shouldDisplayActivityDialog.value = flag
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

    fun saveActivity(diaryDao: DiaryDao) {
        viewModelScope.launch(Dispatchers.IO) {
            diaryDao.insertDiaryEntry(DiaryEntryEntity(_label.value, 0 - _calories.value.toInt(), Calendar.getInstance().time.toString()))
            setCaloricValues(_currentCalories.value - _calories.value.toInt(), UserData.requiredCalories)
        }
    }

    fun saveFood(diaryDao: DiaryDao) {
        viewModelScope.launch(Dispatchers.IO) {
            diaryDao.insertDiaryEntry(DiaryEntryEntity(_label.value, _calories.value.toInt(), Calendar.getInstance().time.toString()))
            setCaloricValues(_currentCalories.value - _calories.value.toInt(), UserData.requiredCalories)
        }
    }

    fun setLabel(label: String) {
        _label.value = label
    }

    fun setCalories(calories: String) {
        _calories.value = calories
    }
}
