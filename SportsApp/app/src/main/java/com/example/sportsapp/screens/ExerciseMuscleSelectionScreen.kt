package com.example.sportsapp.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.R
import com.example.sportsapp.components.BaseCard
import com.example.sportsapp.navigation.WorkoutScreens
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.viewmodels.ExerciseViewModel

@Composable
fun ExerciseMuscleSelection(navController: NavController = rememberNavController(), viewModel: ExerciseViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    Box(
        modifier = Modifier.fillMaxSize().background(PrimaryColorNavy),
    ) {
        Column {
            Box(Modifier.verticalScroll(rememberScrollState())) {
                Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    MuscleGroupItemCard(
                        modifier = Modifier,
                        muscleGroup = "Biceps",
                        resource = R.drawable.intermediate_difficulty,
                    ) {
                        viewModel.setMuscle("biceps")
                        navController.navigate(WorkoutScreens.ExerciseResults.route)
                    }
                    MuscleGroupItemCard(
                        modifier = Modifier,
                        muscleGroup = "Abs",
                        resource = R.drawable.intermediate_difficulty,
                    ) {
                        viewModel.setMuscle("abdominals")
                        navController.navigate(WorkoutScreens.ExerciseResults.route)
                    }
                    MuscleGroupItemCard(
                        modifier = Modifier,
                        muscleGroup = "Chest",
                        resource = R.drawable.intermediate_difficulty,
                    ) {
                        viewModel.setMuscle("chest")
                        navController.navigate(WorkoutScreens.ExerciseResults.route)
                    }
                    MuscleGroupItemCard(
                        modifier = Modifier,
                        muscleGroup = "Back",
                        resource = R.drawable.intermediate_difficulty,
                    ) {
                        viewModel.setMuscle("middle_back")
                        navController.navigate(WorkoutScreens.ExerciseResults.route)
                    }
                    MuscleGroupItemCard(
                        modifier = Modifier,
                        muscleGroup = "Legs",
                        resource = R.drawable.intermediate_difficulty,
                    ) {
                        viewModel.setMuscle("calves")
                        navController.navigate(WorkoutScreens.ExerciseResults.route)
                    }
                }
            }
        }
    }
}

@Composable
fun MuscleGroupItemCard(modifier: Modifier, muscleGroup: String, resource: Int, onClick: () -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    Box(
        modifier = modifier.fillMaxWidth()
            .height(screenHeight / 5)
            .clickable { onClick() },
        contentAlignment = Alignment.BottomEnd,
    ) {
        BaseCard(modifier = Modifier.fillMaxSize(), backgroundColor = Color.Black) {
            Image(
                modifier = Modifier.alpha(0.6f).matchParentSize(),
                painter = painterResource(id = resource),
                contentDescription = "muscle group picture",
                contentScale = ContentScale.Crop,
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = muscleGroup,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
fun PreviewExerciseMuscleSelection() {
    ExerciseMuscleSelection()
}
