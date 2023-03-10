package com.example.sportsapp.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.sportsapp.data.BottomBarScreen
import com.example.sportsapp.screens.FoodScreen
import com.example.sportsapp.screens.SearchFoodScreen

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route,
    ) {
        composable(route = BottomBarScreen.Home.route) {
            Text(text = BottomBarScreen.Home.route)
        }
        composable(route = BottomBarScreen.Profile.route) {
            Text(text = BottomBarScreen.Profile.route)
        }
        composable(route = BottomBarScreen.Food.route) {
            FoodScreen(navController)
        }
        composable(route = FoodScreens.FoodSearch.route) {
            SearchFoodScreen()
        }

        // foodNavGraph(navController)
    }
}

fun NavGraphBuilder.foodNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.FOOD,
        startDestination = BottomBarScreen.Food.route,
    ) {
        composable(route = FoodScreens.FoodSearch.route) {
            SearchFoodScreen()
        }
    }
}
