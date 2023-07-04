package com.example.sportsapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.components.PaginatedLazyColumn
import com.example.sportsapp.components.RoundBottomCard
import com.example.sportsapp.components.SavedMealPlanItem
import com.example.sportsapp.data.Exercise
import com.example.sportsapp.entity.ExerciseEntity
import com.example.sportsapp.navigation.WorkoutScreens
import com.example.sportsapp.ui.theme.LoginFormTypography
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.viewmodels.FavoriteExerciseViewModel

@Composable
fun FavoriteExerciseScreen(navController: NavController = rememberNavController(), viewModel: FavoriteExerciseViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val favoriteExercises by viewModel.favoriteExercises.collectAsState()
    val refreshFlag by viewModel.refreshFlag.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.observeFavoriteExercises()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColorNavy),
    ) {
        Column {
            RoundBottomCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.White,
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .clickable { },
                    text = "Saved Exercises",
                    textAlign = TextAlign.Center,
                    style = LoginFormTypography.body1,
                )
            }

            Spacer(modifier = Modifier.size(20.dp))

            if (!refreshFlag) {
                PaginatedLazyColumn(
                    itemList = favoriteExercises,
                    itemLayout = {
                        val exercise = it as ExerciseEntity
                        SavedMealPlanItem(title = exercise.name, onClick = {
                            navController.currentBackStackEntry?.arguments?.putParcelable("exercise", Exercise(exercise.name, exercise.type, exercise.muscle, exercise.equipment, exercise.difficulty, exercise.instructions))
                            navController.navigate(WorkoutScreens.ExerciseDetails.route)
                        }) {
                            viewModel.onDeleteClick(exercise)
                        }
                    },

                    onScrollToEnd = {},
                )
            } else {
                viewModel.triggerRefreshFlag()
            }
        }
    }
}

@Preview
@Composable
fun PreviewFavoriteExerciseScreen() {
}
