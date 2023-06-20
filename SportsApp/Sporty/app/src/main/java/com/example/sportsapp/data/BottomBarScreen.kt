package com.example.sportsapp.data

import com.example.sportsapp.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
) {
    object Home : BottomBarScreen(
        route = "HOME",
        title = "HOME",
        icon = R.drawable.baseline_home_24,
    )

    object Workouts : BottomBarScreen(
        route = "WORKOUTS",
        title = "WORKOUTS",
        icon = R.drawable.baseline_fitness_center_24_navy,
    )

    object Food : BottomBarScreen(
        route = "FOOD",
        title = "FOOD",
        icon = R.drawable.ic_food,
    )
}
