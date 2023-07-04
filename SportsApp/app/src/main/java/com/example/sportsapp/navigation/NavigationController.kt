package com.example.sportsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.screens.*
import com.example.sportsapp.viewmodels.LoginViewModel
import com.example.sportsapp.viewmodels.RegisterViewModel

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

        registrationGraph(navController)
    }
}

fun NavGraphBuilder.registrationGraph(navController: NavController) {
    val registerViewModel = RegisterViewModel()
    navigation(
        route = Graph.REGISTER,
        startDestination = RegisterScreens.GoalSetting.route,
    ) {
        composable(route = RegisterScreens.GoalSetting.route) {
            GoalsScreen(navController, registerViewModel)
        }

        composable(route = RegisterScreens.ActivityLevelSetting.route) {
            ActivityLevelScreen(navController, registerViewModel)
        }

        composable(route = RegisterScreens.AboutYouSetting.route) {
           AboutYouScreen(navController, registerViewModel)
        }

        composable(route = RegisterScreens.RegisterScreen.route) {
           RegisterScreen(navController, registerViewModel)
        }
    }
}

sealed class FoodScreens(val route: String) {
    object FoodSearch : FoodScreens(route = "SEARCH")
    object FoodDetails : FoodScreens(route = "DETAILS")
    object FoodFavorites : FoodScreens(route = "FOODFAVORITE")

    object SavedMealPlanScreen : FoodScreens(route = "SAVEDPLANVIEW")
    object SaveMealPlans : FoodScreens(route = "SAVEDPLANS")
    object MealPlannerCategory : FoodScreens(route = "PLANNERCATEGORY")
    object MealPlanGenerated : FoodScreens(route = "GENERATEDPLAN")
}

sealed class WorkoutScreens(val route: String) {
    object ExerciseDifficulty : WorkoutScreens(route = "EXERCISEDIFFICULTY")
    object ExerciseMuscleGroupSelection : WorkoutScreens(route = "EXERCISEMUSCLESELECTION")
    object ExerciseResults : WorkoutScreens(route = "EXERCISERESULTS")

    object ExerciseSearch : WorkoutScreens(route = "EXERCISESEARCH")
    object ExerciseSaved : WorkoutScreens(route = "EXERCISESAVED")
    object ExerciseDetails : WorkoutScreens(route = "EXERCISEDETAILS")
}

sealed class RegisterScreens(val route: String) {
    object GoalSetting : RegisterScreens(route = "GOALS")
    object ActivityLevelSetting : RegisterScreens(route = "ACTIVITY")
    object AboutYouSetting : RegisterScreens(route = "ABOUTYOU")
    object RegisterScreen : RegisterScreens(route = "REGISTER")
}
