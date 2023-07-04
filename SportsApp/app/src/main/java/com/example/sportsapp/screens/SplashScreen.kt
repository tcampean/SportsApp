package com.example.sportsapp.screens

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sportsapp.R
import com.example.sportsapp.components.* // ktlint-disable no-wildcard-imports
import com.example.sportsapp.database.AppDatabase
import com.example.sportsapp.navigation.Graph
import com.example.sportsapp.ui.theme.LoginFormTypography
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.ui.theme.SecondaryColor
import com.example.sportsapp.ui.theme.StaticTextTypography
import com.example.sportsapp.viewmodels.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController?, viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val visib by viewModel.visib.collectAsState()
    val loginVisib by viewModel.shouldDisplayLoginForm.collectAsState()
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginErrorMessage by viewModel.loginError.collectAsState()
    val shouldDisplayProgressBar by viewModel.shouldDisplayProgressBar.collectAsState()

    val context = LocalContext.current
    val sharedPref = remember { context.getSharedPreferences("userpref", Context.MODE_PRIVATE) }
    val logged = remember { sharedPref.getBoolean("logged", false) }

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
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
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
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp, top = 50.dp),
                ) {
                    BaseCard(
                        modifier = Modifier.fillMaxHeight(),
                        backgroundColor = Color.White,
                        strokeColor = Color.White,
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 20.dp),
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
                                value = username,
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
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),
                                value = password,
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

                            if (loginErrorMessage.isNotEmpty()) {
                                Text(
                                    modifier = Modifier
                                        .padding(
                                            top = 10.dp,
                                            start = 10.dp,
                                            end = 10.dp,
                                        )
                                        .align(Alignment.CenterHorizontally),
                                    text = loginErrorMessage,
                                    style = LoginFormTypography.body1,
                                    color = SecondaryColor,
                                )
                            }
                            if (shouldDisplayProgressBar) {
                                CircularProgressIndicator(Modifier.padding(top = 10.dp).size(40.dp).align(Alignment.CenterHorizontally), color = SecondaryColor)
                            } else {
                                BaseButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 80.dp, top = 30.dp, end = 80.dp)
                                        .height(50.dp),
                                    backgroundColor = PrimaryColorNavy,
                                    onClick = {
                                        if (username.isEmpty() || password.isEmpty()) {
                                            viewModel.setLoginErrorMessage("Fields can not be empty!")
                                            return@BaseButton
                                        }
                                        viewModel.logIn(AppDatabase.getInstance(context).userDao()) {
                                            sharedPref.edit().putBoolean("logged", true).apply()
                                            navController?.popBackStack()
                                            navController?.navigate(Graph.HOME)
                                        }
                                    },
                                ) {
                                    Text(text = "Login", style = StaticTextTypography.body1)
                                }
                            }

                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    text = "Don't have an account?",
                                    fontSize = 22.sp,
                                    style = StaticTextTypography.body1,
                                    color = PrimaryColorNavy,

                                )

                                Text(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .clickable {
                                            navController?.navigate(Graph.REGISTER)
                                        },
                                    text = "Sign Up!",
                                    fontSize = 22.sp,
                                    style = StaticTextTypography.body1,
                                    color = SecondaryColor,
                                )
                            }
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
                if (logged) {
                    viewModel.getUserInfo(AppDatabase.getInstance(context).userDao())
                    navController?.popBackStack()
                    navController?.navigate(Graph.HOME)
                } else {
                    viewModel.changeLoginVisib()
                }
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
