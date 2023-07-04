package com.example.sportsapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.api.MainAPI
import com.example.sportsapp.components.InputTextField
import com.example.sportsapp.components.UserInputDetailsTemplateScreen
import com.example.sportsapp.data.requestData.RegisterData
import com.example.sportsapp.navigation.AppScreens
import com.example.sportsapp.navigation.RegisterScreens
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.viewmodels.RegisterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

@Composable
fun RegisterScreen(navController: NavController = rememberNavController(), viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val passwordInput by viewModel.passwordInput.collectAsState()
    val usernameInput by viewModel.usernameInput.collectAsState()
    val context = LocalContext.current

    UserInputDetailsTemplateScreen(
        title = "Register",
        indicatorPosition = 4,
        description = "Register your account",
        buttonText = "REGISTER",
        buttonClick = {
            GlobalScope.launch(Dispatchers.IO) {
                val result = MainAPI.retrofitService.register(
                    RegisterData(
                        username = viewModel.usernameInput.value,
                        password = viewModel.passwordInput.value,
                        goal = viewModel.selectedGoal.value,
                        age = viewModel.ageInput.value.toInt(),
                        height = viewModel.heightInput.value.toInt(),
                        weight = viewModel.weightInput.value.toInt(),
                        gender = viewModel.selectedGender.value,
                        activity_level = viewModel.selectedActivity.value,
                    ),
                ).awaitResponse()
                if (result.isSuccessful) {
                    launch(Dispatchers.Main) {
                        Toast.makeText(context, "Account created successfully!", Toast.LENGTH_LONG).show()
                        navController.navigate(AppScreens.Splash.name) {
                            popUpTo(0)
                        }
                    }
                } else {
                    launch(Dispatchers.Main) {
                        Toast.makeText(context, "This username already exists!", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        },
    ) {
        val focusManager = LocalFocusManager.current
        Spacer(modifier = Modifier.size(20.dp))
        Text(text = "Username", fontSize = 20.sp, color = Color.White)
        Spacer(modifier = Modifier.size(10.dp))
        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            value = usernameInput,
            textStyle = TextStyle.Default,
            placeholder = "Your Username",
            onValueChange = { viewModel.onUsernameInputChange(it) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Email),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = PrimaryColorNavy,
                backgroundColor = Color.White,
                cursorColor = PrimaryColorNavy,
                placeholderColor = PrimaryColorNavy,
                focusedBorderColor = Color.Unspecified,
            ),
        )

        Spacer(modifier = Modifier.size(20.dp))
        Text(text = "Password", fontSize = 20.sp, color = Color.White)
        Spacer(modifier = Modifier.size(10.dp))
        InputTextField(
            modifier = Modifier.fillMaxWidth(),
            value = passwordInput,
            textStyle = TextStyle.Default,
            placeholder = "Your Password",
            onValueChange = { viewModel.onPasswordInputChange(it) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            visualTransformation = PasswordVisualTransformation(),
            textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = PrimaryColorNavy,
                backgroundColor = Color.White,
                cursorColor = PrimaryColorNavy,
                placeholderColor = PrimaryColorNavy,
                focusedBorderColor = Color.Unspecified,

            ),
        )
    }
}

@Preview
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen()
}
