package com.example.sportsapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.sportsapp.data.LoginFormData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel() : ViewModel() {

    private val _visib = MutableStateFlow(false)
    val visib = _visib.asStateFlow()

    private val _shouldDisplayLoginForm = MutableStateFlow(false)
    val shouldDisplayLoginForm = _shouldDisplayLoginForm.asStateFlow()

    private val _formData = MutableStateFlow(LoginFormData())
    val formData = _formData.asStateFlow()

    fun onUsernameChange(username: String) {
        _formData.value = _formData.value.copy(username = username)
        Log.d("tag", "DATA IS " + _formData.value)
    }

    fun onPasswordChange(password: String) {
        _formData.value = _formData.value.copy(password = password)
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
}
