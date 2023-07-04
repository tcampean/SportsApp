package com.example.sportsapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportsapp.api.MainAPI
import com.example.sportsapp.data.LoginFormData
import com.example.sportsapp.data.UserData
import com.example.sportsapp.data.requestData.UserDetails
import com.example.sportsapp.database.UserDao
import com.example.sportsapp.entity.UserDataEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.await

class LoginViewModel() : ViewModel() {

    private val _visib = MutableStateFlow(false)
    val visib = _visib.asStateFlow()

    private val _shouldDisplayLoginForm = MutableStateFlow(false)
    val shouldDisplayLoginForm = _shouldDisplayLoginForm.asStateFlow()

    private val _formData = MutableStateFlow(LoginFormData())
    val formData = _formData.asStateFlow()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _loginError = MutableStateFlow("")
    val loginError = _loginError.asStateFlow()

    private val _shouldDisplayProgressBar = MutableStateFlow(false)
    val shouldDisplayProgressBar = _shouldDisplayProgressBar.asStateFlow()

    fun onUsernameChange(username: String) {
        _username.value = username
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun setLoginErrorMessage(message: String) {
        _loginError.value = message
    }

    fun changeVisib() {
        System.out.print("Changed in vm")
        Log.d("HERRE", "HERE IN VM")
        _visib.value = !_visib.value
    }

    fun changeLoginVisib() {
        System.out.print("Changed in vm 2")
        Log.d("HERRE", "HERE IN VM 2")
        _shouldDisplayLoginForm.value = !_shouldDisplayLoginForm.value
    }

    fun logIn(userDao: UserDao, onSuccessfulResponse: (UserDetails) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _shouldDisplayProgressBar.value = true
            var result: UserDetails
            try {
                result = MainAPI.retrofitService.login(_username.value, password.value).await()
                _loginError.value = ""
                userDao.insertUser(UserDataEntity(result.goal, result.activity_level, result.gender, result.weight, result.height, result.age, _username.value))
                launch(Dispatchers.Main) {
                    onSuccessfulResponse(result)
                }
            } catch (e: HttpException) {
                _loginError.value = "Wrong credentials!"
            }
            _shouldDisplayProgressBar.value = false
        }
    }

    fun getUserInfo(dao: UserDao) {
        viewModelScope.launch(Dispatchers.IO) {
            UserData.user = dao.getUser()[0]
        }
    }
}
