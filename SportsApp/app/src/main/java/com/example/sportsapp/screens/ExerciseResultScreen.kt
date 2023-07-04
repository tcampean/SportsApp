package com.example.sportsapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.components.ExerciseItem
import com.example.sportsapp.components.PaginatedLazyColumn
import com.example.sportsapp.data.Exercise
import com.example.sportsapp.navigation.WorkoutScreens
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.ui.theme.SecondaryColor
import com.example.sportsapp.viewmodels.ExerciseViewModel

@Composable
fun ExerciseResultScreen(navController: NavController = rememberNavController(), viewModel: ExerciseViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val shouldDisplayProgressBar by viewModel.shouldDisplayProgressBar.collectAsState()
    val exerciseList by viewModel.exerciseList.collectAsState()

    LaunchedEffect(Unit) {
        if (exerciseList.isEmpty()) {
            viewModel.offset = 0
            viewModel.getExercises()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColorNavy),
    ) {
        if (shouldDisplayProgressBar) {
            CircularProgressIndicator(
                Modifier
                    .size(LocalConfiguration.current.screenHeightDp.dp / 8)
                    .align(Alignment.Center),
                color = SecondaryColor,
            )
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(15.dp)) {
                PaginatedLazyColumn(
                    itemList = exerciseList,
                    itemLayout = {
                        val exercise = it as Exercise
                        ExerciseItem(exercise = exercise, onClick = {
                            viewModel.setCurrentExercise(exercise)
                            navController.navigate(WorkoutScreens.ExerciseDetails.route)
                        })
                    },
                ) {
                    viewModel.requestDataAppend()
                }
            }
        }
    }
}
