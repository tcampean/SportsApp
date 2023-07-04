package com.example.sportsapp.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.R
import com.example.sportsapp.components.BaseCard
import com.example.sportsapp.components.ButtonProgressbar
import com.example.sportsapp.components.CircularProgressbar1
import com.example.sportsapp.components.RoundBottomCard
import com.example.sportsapp.components.WeightUpdateDialog
import com.example.sportsapp.data.UserData
import com.example.sportsapp.database.AppDatabase
import com.example.sportsapp.navigation.AppScreens
import com.example.sportsapp.ui.theme.LoginFormTypography
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.ui.theme.StaticTextTypography
import com.example.sportsapp.viewmodels.HomeViewModel

@Composable
fun HomeScreen(navController: NavController = rememberNavController(), viewModel: HomeViewModel) {
    val context = LocalContext.current
    val current by viewModel.currentCalories.collectAsState()
    val remaining by viewModel.remainingCalories.collectAsState()
    val weight by viewModel.weight.collectAsState()
    val shouldDisplayWeightDialog by viewModel.shouldDisplayWeightDialog.collectAsState()
    val weightDialog by viewModel.weightDialog.collectAsState()
    val currentString by viewModel.currentCaloriesString.collectAsState()
    val remainingString by viewModel.remainingCaloriesString.collectAsState()
    val refreshFlag by viewModel.refreshFlag.collectAsState()
    val sharedPref = remember { context.getSharedPreferences("userpref", Context.MODE_PRIVATE) }

    viewModel.setDialogWeight(weight)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColorNavy),
    ) {
        if(shouldDisplayWeightDialog) {
            WeightUpdateDialog(
                value = weightDialog,
                setShowDialog = { viewModel.setWeightDialogDisplayFlag(it) },
                onPlusClicked = { viewModel.setDialogWeight(1) },
                onMinusClicked = { viewModel.setDialogWeight(-1) },
                onSaveClicked = {
                    viewModel.setWeight(weightDialog, AppDatabase.getInstance(context).userDao())
                }
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            RoundBottomCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.White,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.clickable {
                            sharedPref.edit().putBoolean("logged", false).apply()
                            viewModel.signOut(AppDatabase.getInstance(context).userDao())
                            navController.navigate(AppScreens.Splash.name) {
                                popUpTo(0)
                            }
                        },
                        text = "Sign out",
                        style = LoginFormTypography.body1,
                    )
                }
            }
            Box(Modifier.align(Alignment.CenterHorizontally)) {
                CircularProgressbar1(total = 2000f, dataUsage = current.toFloat(), remaining = remaining.toFloat())
                if (refreshFlag) {
                    Column(
                        Modifier.align(Alignment.Center),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                            Text(
                                text = currentString,
                                color = Color.Black,
                                style = StaticTextTypography.body1,
                                textAlign = TextAlign.Center,
                                fontSize = 30.sp
                            )

                        Text(
                            text = "$remainingString left to go",
                            color = Color.Black,
                            style = StaticTextTypography.body1,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                        )
                    }
                } else {
                    viewModel.triggerReset()
                }
            }
            ButtonProgressbar {
                val data = (0 until 2000.toInt() + 1).random()
                viewModel.setCaloricValues(data, 2000)
                viewModel.triggerReset()
            }

            BaseCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 5.dp),
                backgroundColor = Color.White,
                strokeColor = Color.Transparent,
                cornerSize = 10
            ) {
                Column {
                    Spacer(modifier = Modifier.weight(1f))
                    Row(Modifier.padding(start = 10.dp), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text(text = "Current weight: ", style = LoginFormTypography.body1, fontSize = 18.sp)
                        Text(text = "${weight} kgs", style = LoginFormTypography.body1, fontSize = 18.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Image(modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .clickable {
                                viewModel.setWeightDialogDisplayFlag(true)
                            }, painter = painterResource(id = R.drawable.baseline_edit_24), contentDescription = "edit")


                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(viewModel = HomeViewModel())
}
