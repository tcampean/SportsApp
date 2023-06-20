package com.example.sportsapp.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.sportsapp.data.BottomBarScreen
import com.example.sportsapp.database.AppDatabase
import com.example.sportsapp.repository.MealRepository
import com.example.sportsapp.screens.*
import com.example.sportsapp.viewmodels.ExerciseViewModel
import com.example.sportsapp.viewmodels.FavoriteMealViewModel
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
        composable(route = BottomBarScreen.Workouts.route) {
            WorkoutScreen(navController)
        }
        composable(route = BottomBarScreen.Food.route) {
            FoodScreen(navController)
        }

        foodNavGraph(navController)
        workoutNavGraph(navController)
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
            viewModel.setDao(AppDatabase.getInstance(LocalContext.current).mealDao())
            FoodDetailsScreen(viewModel = viewModel)
        }

        composable(route = FoodScreens.MealPlannerCategory.route) {
            MealPlanCategoryScreen(navController, mealPlanViewModel)
        }

        composable(route = FoodScreens.MealPlanGenerated.route) {
            MealPlanScreen(navController, mealPlanViewModel)
        }

        composable(route = FoodScreens.FoodFavorites.route) {
            val viewModel: FavoriteMealViewModel = viewModel()
            viewModel.setRepository(MealRepository(AppDatabase.getInstance(LocalContext.current).mealDao()))
            FavoriteMealScreen(navController, viewModel)
        }
    }
}

fun NavGraphBuilder.workoutNavGraph(navController: NavHostController) {
    val exerciseViewModel = ExerciseViewModel()

    navigation(
        route = Graph.WORKOUT,
        startDestination = BottomBarScreen.Workouts.route,
    ) {
        composable(route = WorkoutScreens.ExerciseDifficulty.route) {
            ExerciseDifficultyScreen(navController, exerciseViewModel)
        }

        composable(route = WorkoutScreens.ExerciseMuscleGroupSelection.route) {
            ExerciseMuscleSelection(navController, exerciseViewModel)
        }

        composable(route = WorkoutScreens.ExerciseResults.route) {
            ExerciseResultScreen(navController, exerciseViewModel)
        }
    }
}
