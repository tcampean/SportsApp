package com.example.sportsapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.components.PaginatedLazyColumn
import com.example.sportsapp.components.RoundBottomCard
import com.example.sportsapp.components.SavedMealPlanItem
import com.example.sportsapp.entity.DayMealPlanEntity
import com.example.sportsapp.entity.WeekMealPlanEntity
import com.example.sportsapp.navigation.FoodScreens
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.viewmodels.FavoriteMealPlanViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun FavoriteMealPlanScreen(navController: NavHostController = rememberNavController(), viewModel: FavoriteMealPlanViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val savedDayPlans by viewModel.savedDayMeals.collectAsState()
    val savedWeekPlans by viewModel.savedWeekMeals.collectAsState()
    val mealPlanLength by viewModel.mealPlanLength.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.observeMealPlans()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColorNavy),
        contentAlignment = Alignment.Center,
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(20.dp)) {
            RoundBottomCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.White,
            ) {
                MealPlanTab(onTabChanged = {
                    viewModel.setMealPlanLength(it)
                })
            }

            if (mealPlanLength == "day") {
                PaginatedLazyColumn(itemList = savedDayPlans, itemLayout = {
                    val mealPlan = it as DayMealPlanEntity
                    SavedMealPlanItem(
                        title = mealPlan.name,
                        onClick = {
                            viewModel.setCurrentDayMealPlan(mealPlan)
                            navController.navigate(FoodScreens.SavedMealPlanScreen.route)
                        },
                        onDeleteClick = {
                            GlobalScope.launch(Dispatchers.IO) {
                                viewModel.getRepository().deleteDayPlan(mealPlan)
                            }
                        },
                    )
                }) {
                }
            } else {
                PaginatedLazyColumn(itemList = savedWeekPlans, itemLayout = {
                    val mealPlan = it as WeekMealPlanEntity
                    SavedMealPlanItem(
                        title = mealPlan.name,
                        onClick = {
                            viewModel.setCurrentWeekMealPlan(mealPlan)
                            navController.navigate(FoodScreens.SavedMealPlanScreen.route)
                        },
                        onDeleteClick = {
                            GlobalScope.launch(Dispatchers.IO) {
                                viewModel.getRepository().deleteWeekPlan(mealPlan)
                            }
                        },
                    )
                }) {
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewFavoriteMealPlanScreen() {
    FavoriteMealPlanScreen()
}
