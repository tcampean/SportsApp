package com.example.sportsapp.navigation

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
import com.example.sportsapp.data.Exercise
import com.example.sportsapp.data.RecipeDetailed
import com.example.sportsapp.data.UserData
import com.example.sportsapp.database.AppDatabase
import com.example.sportsapp.repository.DiaryRepository
import com.example.sportsapp.repository.ExerciseRepository
import com.example.sportsapp.repository.MealPlanRepository
import com.example.sportsapp.repository.MealRepository
import com.example.sportsapp.screens.*
import com.example.sportsapp.viewmodels.*

@Composable
fun MainNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route,
    ) {
        composable(route = BottomBarScreen.Home.route) {
            val vm: HomeViewModel = viewModel()
            vm.setRepository(DiaryRepository(AppDatabase.getInstance(LocalContext.current).diaryDao()))
            vm.setDialogWeight(UserData.user.weight)
            HomeScreen(navController, vm)
        }
        composable(route = BottomBarScreen.Workouts.route) {
            WorkoutScreen(navController)
        }
        composable(route = BottomBarScreen.Food.route) {
            FoodScreen(navController)
        }

        composable(route = AppScreens.Splash.name) {
            SplashScreen(navController = navController)
        }

        registrationGraph(navController)

        foodNavGraph(navController)
        workoutNavGraph(navController)
    }
}

fun NavGraphBuilder.foodNavGraph(navController: NavHostController) {
    val mealPlanViewModel = MealPlanViewModel()
    val favoriteMealPlanViewModel = FavoriteMealPlanViewModel()

    navigation(
        route = Graph.FOOD,
        startDestination = BottomBarScreen.Food.route,
    ) {
        composable(route = FoodScreens.FoodSearch.route) {
            SearchFoodScreen(navController)
        }

        composable(route = FoodScreens.FoodDetails.route) {
            val viewModel: FoodDetailsViewModel = viewModel()
            val id = remember {
                navController.previousBackStackEntry?.arguments?.getInt("ID")
            }

            var meal: RecipeDetailed

            if (id == 0) {
                meal = remember {
                    navController.previousBackStackEntry?.arguments?.getParcelable("meal")!!
                }
                viewModel.setCurrentRecipe(meal)
                println("got here")
            } else {
                viewModel.setRecipeId(id!!)
            }
            viewModel.setDao(AppDatabase.getInstance(LocalContext.current).mealDao())
            FoodDetailsScreen(viewModel = viewModel)
        }

        composable(route = FoodScreens.MealPlannerCategory.route) {
            MealPlanCategoryScreen(navController, mealPlanViewModel)
        }

        composable(route = FoodScreens.MealPlanGenerated.route) {
            mealPlanViewModel.setDao(AppDatabase.getInstance(LocalContext.current).mealPlanDao())
            MealPlanScreen(navController, mealPlanViewModel)
        }

        composable(route = FoodScreens.FoodFavorites.route) {
            val viewModel: FavoriteMealViewModel = viewModel()
            viewModel.setRepository(MealRepository(AppDatabase.getInstance(LocalContext.current).mealDao()))
            FavoriteMealScreen(navController, viewModel)
        }

        composable(route = FoodScreens.SaveMealPlans.route) {
            favoriteMealPlanViewModel.setRepository(MealPlanRepository(AppDatabase.getInstance(LocalContext.current).mealPlanDao()))
            FavoriteMealPlanScreen(navController, favoriteMealPlanViewModel)
        }

        composable(route = FoodScreens.SavedMealPlanScreen.route) {
            FavoriteMealPlanDisplayScreen(navController, favoriteMealPlanViewModel)
        }
    }
}

fun NavGraphBuilder.workoutNavGraph(navController: NavHostController) {
    val exerciseViewModel = ExerciseViewModel()
    val favoriteExerciseViewModel = FavoriteExerciseViewModel()

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

        composable(route = WorkoutScreens.ExerciseDetails.route) {
            exerciseViewModel.dao = AppDatabase.getInstance(LocalContext.current).exerciseDao()
            var exercise: Exercise? = remember {
                navController.previousBackStackEntry?.arguments?.getParcelable("exercise")
            }
            if (exercise != null) {
                exerciseViewModel.setCurrentExercise(exercise)
            }
            ExerciseDetailsScreen(exerciseViewModel)
        }

        composable(route = WorkoutScreens.ExerciseSaved.route) {
            favoriteExerciseViewModel.setRepository(ExerciseRepository(AppDatabase.getInstance(LocalContext.current).exerciseDao()))
            FavoriteExerciseScreen(navController, favoriteExerciseViewModel)
        }

        composable(route = WorkoutScreens.ExerciseSearch.route) {
            ExerciseSearchScreen(navController)
        }
    }
}
