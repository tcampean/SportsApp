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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.components.BaseCard
import com.example.sportsapp.components.UserInputDetailsTemplateScreen
import com.example.sportsapp.navigation.RegisterScreens
import com.example.sportsapp.ui.theme.SelectionColor
import com.example.sportsapp.ui.theme.StaticTextTypography
import com.example.sportsapp.viewmodels.RegisterViewModel

@Composable
fun ActivityLevelScreen(navController: NavController = rememberNavController(), viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val context = LocalContext.current
    val selectedActivity by viewModel.selectedActivity.collectAsState()
    UserInputDetailsTemplateScreen(
        title = "Activity Level",
        indicatorPosition = 2,
        description = "Tell us how active are you?",
        buttonClick = {
            if (selectedActivity == -1) {
                Toast.makeText(context, "Select one of the above options!", Toast.LENGTH_LONG).show()
            } else {
                navController.navigate(RegisterScreens.AboutYouSetting.route)
            }
        },
    ) {
        Column(Modifier.padding(top = 40.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            for (i in 0 until viewModel.activityList.size) {
                BaseCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight / 10)
                        .clickable {
                            viewModel.setSelectedActivity(i)
                        },
                    strokeColor = if (i == selectedActivity) SelectionColor else Color.White,
                ) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column {
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = viewModel.activityList[i],
                                style = StaticTextTypography.body1,
                                color = Color.White,
                            )
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally).padding(horizontal = 10.dp),
                                text = viewModel.activityDescriptionList[i],
                                style = StaticTextTypography.body1,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                color = Color.White,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ActivityLevelScreenPreview() {
    ActivityLevelScreen()
}
