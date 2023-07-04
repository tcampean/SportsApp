package com.example.sportsapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.R
import com.example.sportsapp.components.ActionCard
import com.example.sportsapp.components.ActionCardLong
import com.example.sportsapp.components.PairedCards
import com.example.sportsapp.navigation.FoodScreens
import com.example.sportsapp.navigation.WorkoutScreens
import com.example.sportsapp.ui.theme.PrimaryColorNavy

@Composable
fun WorkoutScreen(navController: NavHostController = rememberNavController()) {
    Box(
        modifier = Modifier.fillMaxSize().background(PrimaryColorNavy),
        contentAlignment = Alignment.Center,
    ) {
        Column(Modifier.padding(30.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            ActionCardLong(
                modifier = Modifier.fillMaxWidth(),
                color = PrimaryColorNavy,
                borderColor = Color.White,
                label = "Discover exercises",
                textColor = Color.White,
                icon = R.drawable.baseline_fitness_center_24_white,
                onClick = {
                    navController.navigate(WorkoutScreens.ExerciseDifficulty.route)
                },
            )
            PairedCards(
                leftCard = {
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        label = "Search Exercises",
                        icon = R.drawable.baseline_search_24,
                        onClick = {
                            navController.navigate(WorkoutScreens.ExerciseSearch.route)
                        },
                    )
                },
                rightCard = {
                    ActionCard(
                        modifier = Modifier.weight(1f),
                        label = "Saved Exercises",
                        icon = R.drawable.baseline_star_24,
                        onClick = {
                            navController.navigate(WorkoutScreens.ExerciseSaved.route)
                        },
                    )
                },
            )
        }
    }
}

@Preview
@Composable
fun WorkoutScreenPreview() {
    WorkoutScreen()
}
