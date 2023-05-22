package com.example.sportsapp.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import com.example.sportsapp.components.BaseCard
import com.example.sportsapp.components.RoundBottomCard
import com.example.sportsapp.data.MealPlanCategory
import com.example.sportsapp.navigation.FoodScreens
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.ui.theme.SecondaryColor
import com.example.sportsapp.viewmodels.MealPlanViewModel

@Composable
fun MealPlanCategoryScreen(navController: NavHostController = rememberNavController(), viewModel: MealPlanViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    Box(
        modifier = Modifier.fillMaxSize().background(PrimaryColorNavy),
        contentAlignment = Alignment.Center,
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(20.dp)) {
            RoundBottomCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.White,
            ) {
                MealPlanTab(onTabChanged = { viewModel.setMealPlanLength(it) })
            }

            DietGridList(Modifier.fillMaxWidth().padding(10.dp), diets = viewModel.mealPlanCategories, onItemClick = {
                viewModel.setSelectedDiet(it)
                viewModel.resetGenerationLock()
                navController.navigate(route = FoodScreens.MealPlanGenerated.route)
            })
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DietGridList(modifier: Modifier, diets: List<MealPlanCategory>, onItemClick: (String) -> Unit = {}) {
    LazyVerticalGrid(
        modifier = modifier,
        cells = GridCells.Fixed(2),
        content = {
            items(diets) {
                DietCategoryCard(Modifier.padding(7.dp), name = it.name, resource = it.image) {
                    onItemClick(it.name)
                }
            }
        },
    )
}

@Composable
fun DietCategoryCard(modifier: Modifier, name: String, resource: Int, onClick: () -> Unit) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    Box(
        modifier = modifier.width(screenWidth / 2)
            .height(screenHeight / 6)
            .clickable { onClick() },
        contentAlignment = Alignment.BottomEnd,
    ) {
        BaseCard(modifier = Modifier.fillMaxSize(), backgroundColor = Color.Black) {
            Image(
                modifier = Modifier.alpha(0.7f),
                painter = painterResource(id = resource),
                contentDescription = "diet picture",
                contentScale = ContentScale.FillBounds,
            )
        }
        Text(
            modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 15.dp, end = 5.dp),
            text = name,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun MealPlanTab(onTabChanged: (String) -> Unit = {}) {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Day", "Week")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = tabIndex,
            backgroundColor = Color.Transparent,
            contentColor = SecondaryColor,
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title, color = PrimaryColorNavy) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                )
            }
        }
        when (tabIndex) {
            0 -> onTabChanged("day")
            1 -> onTabChanged("week")
        }
    }
}

@Preview
@Composable
fun MealPlanScreenPreview() {
    MealPlanCategoryScreen()
}
