package com.example.sportsapp.screens

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.R
import com.example.sportsapp.components.*
import com.example.sportsapp.data.UserData
import com.example.sportsapp.database.AppDatabase
import com.example.sportsapp.entity.DiaryEntryEntity
import com.example.sportsapp.navigation.AppScreens
import com.example.sportsapp.ui.theme.LoginFormTypography
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.ui.theme.StaticTextTypography
import com.example.sportsapp.viewmodels.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController = rememberNavController(), viewModel: HomeViewModel) {
    val context = LocalContext.current
    val current by viewModel.currentCalories.collectAsState()
    val remaining by viewModel.remainingCalories.collectAsState()
    val weight by viewModel.weight.collectAsState()
    val shouldDisplayWeightDialog by viewModel.shouldDisplayWeightDialog.collectAsState()
    val shouldDisplayFoodDialog by viewModel.shouldDisplayFoodDialog.collectAsState()
    val shouldDisplayActivityDialog by viewModel.shouldDisplayActivityDialog.collectAsState()
    val label by viewModel.label.collectAsState()
    val calories by viewModel.calories.collectAsState()
    val activities by viewModel.activities.collectAsState()
    val food by viewModel.foods.collectAsState()
    val weightDialog by viewModel.weightDialog.collectAsState()
    val currentString by viewModel.currentCaloriesString.collectAsState()
    val remainingString by viewModel.remainingCaloriesString.collectAsState()
    val sharedPref = remember { context.getSharedPreferences("userpref", Context.MODE_PRIVATE) }
    val trigger by viewModel.resetFlag.collectAsState()

    val resetFlag = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.observeMealPlans()
        viewModel.updateCaloricValues()
        resetFlag.value = !resetFlag.value
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColorNavy)
            .verticalScroll(rememberScrollState()),
    ) {
        if (shouldDisplayWeightDialog) {
            WeightUpdateDialog(
                value = weightDialog,
                setShowDialog = { viewModel.setWeightDialogDisplayFlag(it) },
                onPlusClicked = { viewModel.setDialogWeight(weightDialog + 1) },
                onMinusClicked = { viewModel.setDialogWeight(weightDialog - 1) },
                onSaveClicked = {
                    viewModel.setWeight(weightDialog, AppDatabase.getInstance(context).userDao())
                    resetFlag.value = !resetFlag.value
                },
            )
        }
        if (shouldDisplayFoodDialog) {
            DiaryEntryDialog(
                title = "Add a food",
                value = label,
                value2 = calories,
                setShowDialog = { viewModel.setFoodDialogDisplayFlag(it) },
                setValue = { viewModel.setLabel(it) },
                setValue2 = { viewModel.setCalories(it) },
                onSaveClicked = {
                    viewModel.saveFood(AppDatabase.getInstance(context).diaryDao())
                    viewModel.setFoodDialogDisplayFlag(false)
                    viewModel.updateCaloricValues()
                    viewModel.trigger()
                },
            )
        }

        if (shouldDisplayActivityDialog) {
            DiaryEntryDialog(
                title = "Add an activity",
                value = label,
                value2 = calories,
                setShowDialog = { viewModel.setActivityDialogDisplayFlag(it) },
                setValue = { viewModel.setLabel(it) },
                setValue2 = { viewModel.setCalories(it) },
                onSaveClicked = {
                    viewModel.saveActivity(AppDatabase.getInstance(context).diaryDao())
                    viewModel.setActivityDialogDisplayFlag(false)
                    viewModel.updateCaloricValues()
                    viewModel.trigger()
                },
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
                    viewModel.updateCaloricValues()
                    CircularProgressbar1(
                        total = UserData.requiredCalories.toFloat(),
                        dataUsage = current.toFloat(),
                        remaining = remaining.toFloat(),
                    )
                    Column(
                        Modifier.align(Alignment.Center),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        if (trigger) {
                            Text(
                                text = currentString,
                                color = Color.Black,
                                style = StaticTextTypography.body1,
                                textAlign = TextAlign.Center,
                                fontSize = 30.sp,
                            )

                            Text(
                                text = "$remainingString left to go",
                                color = Color.Black,
                                style = StaticTextTypography.body1,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                            )
                            viewModel.trigger()
                        } else {
                            Text(
                                text = currentString,
                                color = Color.Black,
                                style = StaticTextTypography.body1,
                                textAlign = TextAlign.Center,
                                fontSize = 30.sp,
                            )

                            Text(
                                text = "$remainingString left to go",
                                color = Color.Black,
                                style = StaticTextTypography.body1,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                            )
                            viewModel.trigger()
                    }
                }
            }

            BaseCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 5.dp),
                backgroundColor = Color.White,
                strokeColor = Color.Transparent,
                cornerSize = 10,
            ) {
                Column {
                    Spacer(modifier = Modifier.weight(1f))
                    Row(Modifier.padding(start = 10.dp), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text(text = "Current weight: ", style = LoginFormTypography.body1, fontSize = 18.sp)
                        if (resetFlag.value) {
                            Text(
                                text = "$weight kgs",
                                style = LoginFormTypography.body1,
                                fontSize = 18.sp,
                            )
                        } else {
                            Text(
                                text = "$weight kgs",
                                style = LoginFormTypography.body1,
                                fontSize = 18.sp,
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .clickable {
                                    viewModel.setWeightDialogDisplayFlag(true)
                                },
                            painter = painterResource(id = R.drawable.baseline_edit_24),
                            contentDescription = "edit",
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Text(modifier = Modifier.fillMaxWidth(), text = "Your Diary", style = LoginFormTypography.body1, fontWeight = FontWeight.Bold, fontSize = 26.sp, textAlign = TextAlign.Center, color = Color.White)

            Text(modifier = Modifier.padding(start = 15.dp), text = "Foods", style = LoginFormTypography.body1, fontSize = 22.sp, color = Color.White)

            DiaryEntryList(list = food) {
                GlobalScope.launch(Dispatchers.IO) {
                    AppDatabase.getInstance(context).diaryDao().deleteDiaryEntry(it)
                    viewModel.updateCaloricValues()
                    viewModel.trigger()
                }
            }

            BaseCard(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(horizontal = 10.dp)
                    .clickable {
                        viewModel.setLabel("")
                        viewModel.setCalories("")
                        viewModel.setFoodDialogDisplayFlag(true)
                    },
                backgroundColor = Color.White,
                strokeColor = Color.Transparent,
                cornerSize = 10,
            ) {
                Row(Modifier.padding(5.dp)) {
                    Image(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterVertically),
                        painter = painterResource(id = R.drawable.baseline_add_24),
                        contentDescription = "",
                    )

                    Text(text = "Add", style = LoginFormTypography.body1, fontSize = 20.sp, color = PrimaryColorNavy)
                }
            }

            Text(modifier = Modifier.padding(start = 15.dp), text = "Activities", style = LoginFormTypography.body1, fontSize = 22.sp, color = Color.White)

            DiaryEntryList(list = activities) {
                GlobalScope.launch {
                    AppDatabase.getInstance(context).diaryDao().deleteDiaryEntry(it)
                    viewModel.updateCaloricValues()
                    viewModel.trigger()
                }
            }

            BaseCard(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(horizontal = 10.dp)
                    .clickable {
                        viewModel.setLabel("")
                        viewModel.setCalories("")
                        viewModel.setActivityDialogDisplayFlag(true)
                    },
                backgroundColor = Color.White,
                strokeColor = Color.Transparent,
                cornerSize = 10,
            ) {
                Row(Modifier.padding(5.dp)) {
                    Image(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterVertically),
                        painter = painterResource(id = R.drawable.baseline_add_24),
                        contentDescription = "",
                    )

                    Text(text = "Add", style = LoginFormTypography.body1, fontSize = 20.sp, color = PrimaryColorNavy)
                }
            }

            Spacer(modifier = Modifier.size(50.dp))
        }
    }
}

@Composable
fun DiaryEntryList(list: List<DiaryEntryEntity>, onDeleteClick: (DiaryEntryEntity) -> Unit) {
    for (entry in list) {
        SavedMealPlanItem(title = entry.label, onClick = { /*TODO*/ }) {
            onDeleteClick(entry)
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(viewModel = HomeViewModel())
}
