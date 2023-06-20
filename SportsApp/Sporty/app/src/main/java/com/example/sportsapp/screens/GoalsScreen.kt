package com.example.sportsapp.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.components.BaseCard
import com.example.sportsapp.components.UserInputDetailsTemplateScreen
import com.example.sportsapp.navigation.RegisterScreens
import com.example.sportsapp.ui.theme.SelectionColor
import com.example.sportsapp.ui.theme.StaticTextTypography
import com.example.sportsapp.viewmodels.RegisterViewModel

@Composable
fun GoalsScreen(navController: NavController = rememberNavController(), viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val context = LocalContext.current
    val selectedGoal by viewModel.selectedGoal.collectAsState()
    UserInputDetailsTemplateScreen(
        title = "Goals",
        indicatorPosition = 1,
        description = "Let's start with your goal",
        buttonClick = {
            if (selectedGoal == -1) {
                Toast.makeText(context, "Select one of the above goals!", Toast.LENGTH_LONG).show()
            } else {
                navController.navigate(RegisterScreens.ActivityLevelSetting.route)
            }
        },
    ) {
        Column(Modifier.padding(top = 40.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            for (i in 0 until viewModel.goalList.size) {
                BaseCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight / 10)
                        .clickable {
                            viewModel.setSelectedGoal(i)
                        },
                    strokeColor = if (i == selectedGoal) SelectionColor else Color.White,
                ) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = viewModel.goalList[i],
                            style = StaticTextTypography.body1,
                            color = Color.White,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun GoalsScreenPreview() {
    GoalsScreen()
}
