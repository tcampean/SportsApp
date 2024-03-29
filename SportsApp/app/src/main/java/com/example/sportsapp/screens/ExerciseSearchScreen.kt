package com.example.sportsapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.components.ExerciseItem
import com.example.sportsapp.components.PaginatedLazyColumn
import com.example.sportsapp.data.Exercise
import com.example.sportsapp.navigation.WorkoutScreens
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.ui.theme.SecondaryColor
import com.example.sportsapp.viewmodels.SearchExercisesViewModel

@Composable
fun ExerciseSearchScreen(navController: NavController = rememberNavController(), viewModel: SearchExercisesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val exerciseList by viewModel.exerciseList.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val shouldDisplayProgressBar by viewModel.shouldDisplayProgressBar.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize().background(PrimaryColorNavy),
        contentAlignment = Alignment.Center,
    ) {
        if (shouldDisplayProgressBar && viewModel.firstTime) {
            CircularProgressIndicator(
                Modifier
                    .size(LocalConfiguration.current.screenHeightDp.dp / 8)
                    .align(Alignment.Center),
                color = SecondaryColor,
            )
        }
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(20.dp)) {
            SearchBar(
                searchLabel = "Search exercises...",
                searchText = searchText,
                onTextChange = {
                    viewModel.setSearchText(it)
                },
                onSearchButtonClicked = {
                    viewModel.searchRecipe()
                },
            )

            PaginatedLazyColumn(
                exerciseList,
                itemLayout = {
                    val exercise = it as Exercise
                    ExerciseItem(exercise = exercise, onClick = {
                        navController.currentBackStackEntry?.arguments?.putParcelable(
                            "exercise",
                            it,
                        )
                        navController.navigate(WorkoutScreens.ExerciseDetails.route)
                    })
                },
                onScrollToEnd = {
                    viewModel.requestDataAppend()
                },
            )
        }
    }
}

@Preview
@Composable
fun PreviewExerciseSearchScreen() {
    ExerciseSearchScreen()
}
