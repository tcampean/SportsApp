package com.example.sportsapp.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportsapp.R
import com.example.sportsapp.components.BaseCard
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.ui.theme.SecondaryColor
import com.example.sportsapp.viewmodels.ExerciseViewModel

@Composable
fun ExerciseDetailsScreen(viewModel: ExerciseViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val shouldDisplayProgressBar by viewModel.shouldDisplayProgressBar.collectAsState()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val currentExercise by viewModel.currentExercise.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkIfFavorite()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColorNavy)
            .verticalScroll(rememberScrollState()),
    ) {
        if (shouldDisplayProgressBar) {
            CircularProgressIndicator(
                androidx.compose.ui.Modifier
                    .size(screenHeight / 8)
                    .align(Alignment.Center),
                color = SecondaryColor,
            )
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.size(30.dp))
                BaseCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color.White) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                modifier = Modifier.weight(5f),
                                text = currentExercise.name,
                                color = PrimaryColorNavy,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold,
                            )

                            Image(
                                modifier = Modifier
                                    .size(30.dp)
                                    .weight(1f)
                                    .clickable {
                                        viewModel.onSavedPressed()
                                    },
                                painter = painterResource(id = if (isFavorite) R.drawable.heart_full else R.drawable.heart_empty),
                                contentDescription = "favorite",
                            )
                        }

                        Spacer(Modifier.size(5.dp))

                        Text(
                            text = "Type: " + currentExercise.type,
                            color = PrimaryColorNavy,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                        )

                        Spacer(Modifier.size(5.dp))

                        Text(
                            text = "Target Muscle: " + currentExercise.muscle,
                            color = PrimaryColorNavy,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                        )

                        Spacer(Modifier.size(5.dp))

                        Text(
                            text = "Difficulty: " + currentExercise.difficulty,
                            color = PrimaryColorNavy,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                        )

                        Spacer(Modifier.size(20.dp))

                        Text(
                            text = "Instructions",
                            color = PrimaryColorNavy,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                        )

                        Spacer(Modifier.size(5.dp))

                        Text(
                            text = currentExercise.instructions,
                            color = PrimaryColorNavy,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                        )
                    }
                }
                Spacer(modifier = Modifier.size(30.dp))
            }
        }
    }
}

@Preview
@Composable
fun PreviewExerciseDetailsScreen() {
    ExerciseDetailsScreen()
}
