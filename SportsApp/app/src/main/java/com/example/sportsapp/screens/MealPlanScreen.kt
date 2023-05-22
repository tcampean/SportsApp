package com.example.sportsapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.components.BaseCard
import com.example.sportsapp.components.RoundBottomCard
import com.example.sportsapp.data.DayPlan
import com.example.sportsapp.data.Meal
import com.example.sportsapp.data.MealPlanNutrients
import com.example.sportsapp.data.WeekPlan
import com.example.sportsapp.navigation.FoodScreens
import com.example.sportsapp.ui.theme.ItemTypography
import com.example.sportsapp.ui.theme.LoginFormTypography
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.ui.theme.SecondaryColor
import com.example.sportsapp.viewmodels.MealPlanViewModel

@Composable
fun MealPlanScreen(navController: NavController = rememberNavController(), viewModel: MealPlanViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val shouldDisplayProgressBar by viewModel.shouldDisplayProgressBar.collectAsState()
    val currentDayMealPlan by viewModel.currentDayMealPlan.collectAsState()
    val currentWeekMealPlan by viewModel.currentWeekMealPlan.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.generateNewMealPlan()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColorNavy)
            .verticalScroll(rememberScrollState()),
    ) {
        if (shouldDisplayProgressBar) {
            CircularProgressIndicator(
                Modifier
                    .size(LocalConfiguration.current.screenHeightDp.dp / 8)
                    .align(Alignment.Center),
                color = SecondaryColor,
            )
        } else {
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
                        Text(
                            modifier = Modifier.clickable { viewModel.generateNewMealPlan() },
                            text = "New Plan",
                            style = LoginFormTypography.body1,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Save",
                            style = LoginFormTypography.body1,
                        )
                    }
                }

                if (viewModel.isMealPlanLengthDay()) {
                    PlanPerDay(plan = currentDayMealPlan, onMealClick = {
                        navController.currentBackStackEntry?.arguments?.putInt("ID", it)
                        navController.navigate(route = FoodScreens.FoodDetails.route) {
                            launchSingleTop = true
                        }
                    })
                } else {
                    PlanPerWeek(plan = currentWeekMealPlan, onMealClick = {
                        navController.currentBackStackEntry?.arguments?.putInt("ID", it)
                        navController.navigate(route = FoodScreens.FoodDetails.route) {
                            launchSingleTop = true
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun PlanPerDay(plan: DayPlan, onMealClick: (Int) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        for (meal in plan.meals) {
            MealDetailsCard(modifier = Modifier.fillMaxWidth(), meal = meal) {
                onMealClick(meal.id)
            }
        }
        MealNutrientsCard(modifier = Modifier.fillMaxWidth().padding(top = 10.dp), nutrients = plan.nutrients)
    }
}

@Composable
fun PlanPerWeek(plan: WeekPlan, onMealClick: (Int) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        WeekDayCard(modifier = Modifier.fillMaxWidth(), weekDay = "Monday", plan = plan.week.monday, onMealClick = onMealClick)
        WeekDayCard(modifier = Modifier.fillMaxWidth(), weekDay = "Tuesday", plan = plan.week.tuesday, onMealClick = onMealClick)
        WeekDayCard(modifier = Modifier.fillMaxWidth(), weekDay = "Wednesday", plan = plan.week.wednesday, onMealClick = onMealClick)
        WeekDayCard(modifier = Modifier.fillMaxWidth(), weekDay = "Thursday", plan = plan.week.thursday, onMealClick = onMealClick)
        WeekDayCard(modifier = Modifier.fillMaxWidth(), weekDay = "Friday", plan = plan.week.friday, onMealClick = onMealClick)
        WeekDayCard(modifier = Modifier.fillMaxWidth(), weekDay = "Saturday", plan = plan.week.saturday, onMealClick = onMealClick)
        WeekDayCard(modifier = Modifier.fillMaxWidth(), weekDay = "Sunday", plan = plan.week.sunday, onMealClick = onMealClick)
    }
}

@Composable
fun WeekDayCard(modifier: Modifier, weekDay: String, plan: DayPlan, onMealClick: (Int) -> Unit = {}) {
    BaseCard(modifier = modifier, strokeColor = Color.White) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = weekDay, style = ItemTypography.body1, color = Color.White)
            Spacer(modifier = Modifier.size(10.dp))
            PlanPerDay(plan = plan, onMealClick = onMealClick)
        }
    }
}

@Composable
fun MealDetailsCard(modifier: Modifier, meal: Meal, onClick: () -> Unit) {
    BaseCard(
        modifier = modifier
            .clickable { onClick() },
        backgroundColor = Color.White,
    ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(text = meal.title, style = ItemTypography.body2)
            Text(text = "Servings: " + meal.servings, style = ItemTypography.body2)
            Text(text = "Ready In: " + meal.readyInMinutes, style = ItemTypography.body2)
        }
    }
}

@Composable
fun MealNutrientsCard(modifier: Modifier, nutrients: MealPlanNutrients) {
    BaseCard(modifier = modifier, backgroundColor = Color.White) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(text = "Nutrients", style = ItemTypography.body1)
            Column(modifier = Modifier.padding(5.dp)) {
                Text(text = "Calories: " + nutrients.calories, style = ItemTypography.body1)
                Text(text = "Carbohydrates: " + nutrients.carbohydrates, style = ItemTypography.body1)
                Text(text = "Fat: " + nutrients.fat, style = ItemTypography.body1)
                Text(text = "Protein: " + nutrients.protein, style = ItemTypography.body1)
            }
        }
    }
}

@Preview
@Composable
fun PreviewMealPlanScreen() {
    MealPlanScreen()
}
