package com.example.sportsapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.R
import com.example.sportsapp.components.ActionCard
import com.example.sportsapp.components.ActionCardLong
import com.example.sportsapp.components.PairedCards
import com.example.sportsapp.navigation.FoodScreens
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.ui.theme.SecondaryColor

@Composable
fun FoodScreen(navController: NavHostController = rememberNavController()) {
    Box(
        modifier = Modifier.fillMaxSize().background(PrimaryColorNavy),
        contentAlignment = Alignment.Center,
    ) {
        Column(Modifier.padding(30.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            ActionCardLong(
                modifier = Modifier.fillMaxWidth(),
                color = SecondaryColor,
                label = "Get a meal plan",
                icon = R.drawable.schedule,
                onClick = {
                    navController.navigate(FoodScreens.MealPlannerCategory.route)
                },
            )
            PairedCards(
                leftCard = {
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        label = "Search Foods",
                        icon = R.drawable.baseline_search_24,
                        onClick = {
                            navController.navigate(FoodScreens.FoodSearch.route)
                        },
                    )
                },
                rightCard = {
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        label = "Favorite Meals",
                        icon = R.drawable.dinner_favorite,
                        onClick = {
                            navController.navigate(FoodScreens.FoodFavorites.route)
                        },
                    )
                },
            )

            PairedCards(
                leftCard = {
                    Spacer(modifier = Modifier.weight(1f))
                },
                rightCard = {
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        label = "Your Meal Plans",
                        icon = R.drawable.baseline_star_24,
                        onClick = {
                            navController.navigate(FoodScreens.SaveMealPlans.route)
                        },
                    )
                },
            )
        }
    }
}

@Preview
@Composable
fun prev() {
    FoodScreen()
}
