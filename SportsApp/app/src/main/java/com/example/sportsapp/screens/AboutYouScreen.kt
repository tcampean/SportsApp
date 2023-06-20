package com.example.sportsapp.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.components.BaseCard
import com.example.sportsapp.components.InputTextField
import com.example.sportsapp.components.UserInputDetailsTemplateScreen
import com.example.sportsapp.navigation.RegisterScreens
import com.example.sportsapp.ui.theme.*
import com.example.sportsapp.viewmodels.RegisterViewModel

@Composable
fun AboutYouScreen(navController: NavController = rememberNavController(), viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val context = LocalContext.current
    val selectedGender by viewModel.selectedGender.collectAsState()
    val weightInput by viewModel.weightInput.collectAsState()
    val heightInput by viewModel.heightInput.collectAsState()
    val ageInput by viewModel.ageInput.collectAsState()
    UserInputDetailsTemplateScreen(
        title = "About You",
        indicatorPosition = 3,
        description = "Tell us about yourself",
        buttonClick = {
            if (selectedGender.isEmpty() || heightInput.isEmpty() || weightInput.isEmpty()) {
                Toast.makeText(context, "Complete all the data to proceed!", Toast.LENGTH_LONG).show()
            } else {
                navController.navigate(RegisterScreens.RegisterScreen.route)
            }
        },
    ) {
        Column(Modifier.padding(top = 40.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            val focusManager = LocalFocusManager.current
            Text(text = "What is your age?", fontSize = 20.sp, color = Color.White)
            InputTextField(
                modifier = Modifier.fillMaxWidth(),
                value = ageInput,
                textStyle = TextStyle.Default,
                placeholder = "Your Age",
                onValueChange = { viewModel.onAgeInputChange(it) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Number),
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
            Text(text = "What is your gender?", fontSize = 20.sp, color = Color.White)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                BaseCard(
                    modifier = Modifier
                        .height(screenHeight / 12)
                        .clickable {
                            viewModel.setSelectedGender("M")
                        }
                        .weight(1f),
                    strokeColor = if (selectedGender == "M") SelectionColor else Color.White,
                ) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column {
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = "MALE",
                                style = StaticTextTypography.body1,
                                color = Color.White,
                            )
                        }
                    }
                }
                BaseCard(
                    modifier = Modifier
                        .height(screenHeight / 12)
                        .clickable {
                            viewModel.setSelectedGender("F")
                        }
                        .weight(1f),
                    strokeColor = if (selectedGender == "F") SelectionColor else Color.White,
                ) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column {
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = "FEMALE",
                                style = StaticTextTypography.body1,
                                color = Color.White,
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.size(20.dp))
            Text(text = "What is your height in centimeters?", fontSize = 20.sp, color = Color.White)
            InputTextField(
                modifier = Modifier.fillMaxWidth(),
                value = heightInput,
                textStyle = TextStyle.Default,
                placeholder = "Your Height",
                onValueChange = { viewModel.onHeightInputChange(it) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Number),
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
            Text(text = "What is your weight in kilograms?", fontSize = 20.sp, color = Color.White)
            InputTextField(
                modifier = Modifier.fillMaxWidth(),
                value = weightInput,
                textStyle = TextStyle.Default,
                placeholder = "Your Weight",
                onValueChange = { viewModel.onWeightInputChange(it) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
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
}

@Preview
@Composable
fun AboutYouScreenPreview() {
    AboutYouScreen()
}
