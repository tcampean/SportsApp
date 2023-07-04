package com.example.sportsapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.components.BaseCard
import com.example.sportsapp.components.RoundBottomCard
import com.example.sportsapp.data.RecipeDetailed
import com.example.sportsapp.entity.DayMealPlanEntity
import com.example.sportsapp.entity.WeekMealPlanEntity
import com.example.sportsapp.navigation.FoodScreens
import com.example.sportsapp.ui.theme.ItemTypography
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.viewmodels.FavoriteMealPlanViewModel

@Composable
fun FavoriteMealPlanDisplayScreen(navController: NavController = rememberNavController(), viewModel: FavoriteMealPlanViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val currentDayMealPlan by viewModel.currentDayMealPlan.collectAsState()
    val currentWeekMealPlan by viewModel.currentWeekMealPlan.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColorNavy)
            .verticalScroll(rememberScrollState()),
    ) {
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
                }
            }

            if (viewModel.isMealPlanLengthDay()) {
                PlanPerDayMealEntity(plan = currentDayMealPlan, onMealClick = {
                    navController.currentBackStackEntry?.arguments?.putParcelable("meal", it)
                    navController.navigate(route = FoodScreens.FoodDetails.route) {
                        launchSingleTop = true
                    }
                })
            } else {
                PlanPerWeekMealEntity(plan = currentWeekMealPlan, onMealClick = {
                    navController.currentBackStackEntry?.arguments?.putInt("ID", it)
                    navController.navigate(route = FoodScreens.FoodDetails.route) {
                        launchSingleTop = true
                    }
                })
            }
        }
    }
}

@Composable
fun PlanPerDayMealEntity(plan: DayMealPlanEntity, onMealClick: (RecipeDetailed) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        for (meal in plan.meals) {
            RecipeDetailsCard(modifier = Modifier.fillMaxWidth(), meal = meal) {
                onMealClick(meal)
            }
        }
        MealNutrientsCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            nutrients = plan.nutrients,
        )
    }
}

@Composable
fun PlanPerWeekMealEntity(plan: WeekMealPlanEntity, onMealClick: (Int) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        WeekDayCard(modifier = Modifier.fillMaxWidth(), weekDay = "Monday", plan = plan.monday, onMealClick = onMealClick)
        WeekDayCard(modifier = Modifier.fillMaxWidth(), weekDay = "Tuesday", plan = plan.tuesday, onMealClick = onMealClick)
        WeekDayCard(modifier = Modifier.fillMaxWidth(), weekDay = "Wednesday", plan = plan.wednesday, onMealClick = onMealClick)
        WeekDayCard(modifier = Modifier.fillMaxWidth(), weekDay = "Thursday", plan = plan.thursday, onMealClick = onMealClick)
        WeekDayCard(modifier = Modifier.fillMaxWidth(), weekDay = "Friday", plan = plan.friday, onMealClick = onMealClick)
        WeekDayCard(modifier = Modifier.fillMaxWidth(), weekDay = "Saturday", plan = plan.saturday, onMealClick = onMealClick)
        WeekDayCard(modifier = Modifier.fillMaxWidth(), weekDay = "Sunday", plan = plan.sunday, onMealClick = onMealClick)
    }
}

@Composable
fun RecipeDetailsCard(modifier: Modifier, meal: RecipeDetailed, onClick: () -> Unit) {
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
