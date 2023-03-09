package com.example.sportsapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sportsapp.R
import com.example.sportsapp.components.* // ktlint-disable no-wildcard-imports
import com.example.sportsapp.navigation.Graph
import com.example.sportsapp.ui.theme.LoginFormTypography
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.ui.theme.StaticTextTypography
import com.example.sportsapp.viewmodels.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController?, viewModel: LoginViewModel) {
    val visib by viewModel.visib.collectAsState()
    val loginVisib by viewModel.shouldDisplayLoginForm.collectAsState()
    val formData by viewModel.formData.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = "background image",
            contentScale = ContentScale.FillBounds,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.9f)
                .background(Color(0xFF0A1126)),
        ) {}
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Log.d("HERE", "CHANGED IN FUNC")
            if (!loginVisib) {
                Spacer(modifier = Modifier.weight(1f))
            } else {
                Spacer(modifier = Modifier.height(50.dp))
            }
            LogoAnimation(visible = visib) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 50.dp, end = 50.dp),
                    painter = painterResource(id = R.drawable.app_icon),
                    contentDescription = "app icon",
                    contentScale = ContentScale.FillWidth,
                )
            }
            LoginFormAnimation(visible = loginVisib) {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp, top = 50.dp),
                ) {
                    BaseCard(
                        modifier = Modifier.fillMaxHeight(),
                        backgroundColor = Color.White,
                        strokeColor = Color.White,
                    ) {
                        Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    text = "Enter your account",
                                    style = StaticTextTypography.body1,
                                    color = PrimaryColorNavy,
                                )
                            }

                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = formData.username,
                                label = "Username",
                                textStyle = LoginFormTypography.body1,
                                onValueChange = { viewModel.onUsernameChange(it) },
                                textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                                    backgroundColor = Color.White,
                                    focusedBorderColor = PrimaryColorNavy,
                                    unfocusedBorderColor = PrimaryColorNavy,
                                    focusedLabelColor = PrimaryColorNavy,
                                    unfocusedLabelColor = PrimaryColorNavy,
                                ),
                            )

                            TextField(
                                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                                value = formData.password,
                                label = "Password",
                                textStyle = LoginFormTypography.body1,
                                onValueChange = { viewModel.onPasswordChange(it) },
                                textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                                    backgroundColor = Color.White,
                                    focusedBorderColor = PrimaryColorNavy,
                                    unfocusedBorderColor = PrimaryColorNavy,
                                    focusedLabelColor = PrimaryColorNavy,
                                    unfocusedLabelColor = PrimaryColorNavy,
                                ),
                                visualTransformation = PasswordVisualTransformation(),
                            )

                            BaseButton(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(start = 80.dp, top = 30.dp, end = 80.dp)
                                    .height(50.dp),
                                backgroundColor = PrimaryColorNavy,
                                onClick = {},
                            ) {
                                Text(text = "Login", style = StaticTextTypography.body1)
                            }
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Don't have an account?",
                                style = StaticTextTypography.body1,
                                color = PrimaryColorNavy,
                            )
                        }
                    }
                }
            }
            if (!loginVisib) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        LaunchedEffect(key1 = "keeuy", block = {
            launch {
                delay(500)
                viewModel.changeVisib()
                delay(700)
                navController?.popBackStack()
                navController?.navigate(Graph.HOME)
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
fun preview() {
    val viewModel = LoginViewModel()
    viewModel.changeVisib()
    SplashScreen(navController = null, viewModel)
}
