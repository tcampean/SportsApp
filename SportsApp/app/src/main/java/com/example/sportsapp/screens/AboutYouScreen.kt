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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.components.BaseCard
import com.example.sportsapp.components.UserInputDetailsTemplateScreen
import com.example.sportsapp.ui.theme.SelectionColor
import com.example.sportsapp.ui.theme.StaticTextTypography
import com.example.sportsapp.viewmodels.RegisterViewModel

@Composable
fun AboutYouScreen(navController: NavController = rememberNavController(), viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val context = LocalContext.current
    val selectedActivity by viewModel.selectedActivity.collectAsState()
    val selectedGender by viewModel.selectedGender.collectAsState()

    UserInputDetailsTemplateScreen(
        title = "About You",
        indicatorPosition = 3,
        description = "Tell us about yourself",
        buttonClick = {
            if (selectedActivity == -1) {
                Toast.makeText(context, "Complete all the data to proceed!", Toast.LENGTH_LONG).show()
            } else {
            }
        },
    ) {
        Column(Modifier.padding(top = 40.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(text = "What is your gender?", fontSize = 20.sp, color = Color.White)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                BaseCard(
                    modifier = Modifier
                        .height(screenHeight / 12)
                        .clickable {
                        }
                        .weight(1f),
                    strokeColor = Color.White,
                ) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column {
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = "MALE",
                                style = StaticTextTypography.body1,
                                color = if (selectedGender == 0) SelectionColor else Color.White,
                            )
                        }
                    }
                }
                BaseCard(
                    modifier = Modifier
                        .height(screenHeight / 12)
                        .clickable {
                        }
                        .weight(1f),
                    strokeColor = Color.White,
                ) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column {
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = "FEMALE",
                                style = StaticTextTypography.body1,
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
fun AboutYouScreenPreview() {
    AboutYouScreen()
}
