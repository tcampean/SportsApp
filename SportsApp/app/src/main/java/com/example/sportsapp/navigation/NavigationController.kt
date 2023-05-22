package com.example.sportsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.screens.MainScreen
import com.example.sportsapp.screens.SplashScreen
import com.example.sportsapp.viewmodels.LoginViewModel

@Composable
fun NavigationController() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.Splash.name) {
        composable(AppScreens.Splash.name) {
            val viewModel = LoginViewModel()
            SplashScreen(navController = navController, viewModel)
        }

        composable(Graph.HOME) {
            MainScreen()
        }
    }
}

sealed class FoodScreens(val route: String) {
    object FoodSearch : FoodScreens(route = "SEARCH")
    object FoodDetails : FoodScreens(route = "DETAILS")
    object MealPlannerCategory : FoodScreens(route = "PLANNERCATEGORY")
    object MealPlanGenerated : FoodScreens(route = "GENERATEDPLAN")
}
