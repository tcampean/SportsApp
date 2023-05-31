package com.example.sportsapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.R
import com.example.sportsapp.components.BaseCard
import com.example.sportsapp.navigation.WorkoutScreens
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.viewmodels.ExerciseViewModel

@Composable
fun ExerciseDifficultyScreen(navController: NavHostController = rememberNavController(), viewModel: ExerciseViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    Box(
        modifier = Modifier.fillMaxSize().background(PrimaryColorNavy),
        contentAlignment = Alignment.Center,
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            DifficultyItemCard(modifier = Modifier, difficulty = "Beginner", resource = R.drawable.easy_difficulty) {
                viewModel.setDifficulty("Beginner")
                navController.navigate(WorkoutScreens.ExerciseMuscleGroupSelection.route)
            }
            DifficultyItemCard(modifier = Modifier, difficulty = "Intermediate", resource = R.drawable.intermediate_difficulty) {
                viewModel.setDifficulty("Intermediate")
                navController.navigate(WorkoutScreens.ExerciseMuscleGroupSelection.route)
            }
            DifficultyItemCard(modifier = Modifier, difficulty = "Expert", resource = R.drawable.expert_difficulty) {
                viewModel.setDifficulty("Expert")
                navController.navigate(WorkoutScreens.ExerciseMuscleGroupSelection.route)
            }
        }
    }
}

@Composable
fun DifficultyItemCard(modifier: Modifier, difficulty: String, resource: Int, onClick: () -> Unit) {
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
                contentDescription = "diet picture",
                contentScale = ContentScale.Crop,
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = difficulty,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
fun PreviewExerciseDifficultyScreen() {
    ExerciseDifficultyScreen()
}
