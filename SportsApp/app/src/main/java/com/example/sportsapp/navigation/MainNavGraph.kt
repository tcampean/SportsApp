package com.example.sportsapp.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.sportsapp.data.BottomBarScreen
import com.example.sportsapp.screens.FoodDetailsScreen
import com.example.sportsapp.screens.FoodScreen
import com.example.sportsapp.screens.MealPlanCategoryScreen
import com.example.sportsapp.screens.MealPlanScreen
import com.example.sportsapp.screens.SearchFoodScreen
import com.example.sportsapp.viewmodels.FoodDetailsViewModel
import com.example.sportsapp.viewmodels.MealPlanViewModel

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

        foodNavGraph(navController)
    }
}

fun NavGraphBuilder.foodNavGraph(navController: NavHostController) {
    val mealPlanViewModel = MealPlanViewModel()

    navigation(
        route = Graph.FOOD,
        startDestination = BottomBarScreen.Food.route,
    ) {
        composable(route = FoodScreens.FoodSearch.route) {
            SearchFoodScreen(navController)
        }

        composable(route = FoodScreens.FoodDetails.route) {
            val id = remember {
                navController.previousBackStackEntry?.arguments?.getInt("ID")
            }
            val viewModel: FoodDetailsViewModel = viewModel()
            viewModel.setRecipeId(id!!)
            FoodDetailsScreen(viewModel = viewModel)
        }

        composable(route = FoodScreens.MealPlannerCategory.route) {
            MealPlanCategoryScreen(navController, mealPlanViewModel)
        }

        composable(route = FoodScreens.MealPlanGenerated.route) {
            MealPlanScreen(navController, mealPlanViewModel)
        }
    }
}
