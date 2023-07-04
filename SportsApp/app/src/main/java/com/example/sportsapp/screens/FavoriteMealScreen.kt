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
import com.example.sportsapp.components.FavoriteMealLazyColumn
import com.example.sportsapp.components.RoundBottomCard
import com.example.sportsapp.data.RecipeDetailed
import com.example.sportsapp.entity.FavoriteMealEntity
import com.example.sportsapp.navigation.FoodScreens
import com.example.sportsapp.ui.theme.LoginFormTypography
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.viewmodels.FavoriteMealViewModel

@Composable
fun FavoriteMealScreen(navController: NavController = rememberNavController(), viewModel: FavoriteMealViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val favoriteMeals by viewModel.favoriteMeals.collectAsState()
    val refreshFlag by viewModel.refreshFlag.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.observeFavoriteMeals()
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
                    text = "Favorite Meals",
                    textAlign = TextAlign.Center,
                    style = LoginFormTypography.body1,
                )
            }

            Spacer(modifier = Modifier.size(20.dp))

            if(!refreshFlag) {
                FavoriteMealLazyColumn(
                    itemList = favoriteMeals,
                    onItemClick = {
                        navController.currentBackStackEntry?.arguments?.putParcelable("meal", RecipeDetailed(it.id, it.title, it.image, it.imageType, it.servings, it.readyInMinutes, it.extendedIngredients, it.summary, it.nutrition))
                        navController.navigate(route = FoodScreens.FoodDetails.route)
                    },
                    onDeleteClick = {
                        viewModel.onDeleteClick(it)
                        viewModel.triggerRefreshFlag()
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
fun PreviewFavoriteMealScreen() {
    FavoriteMealScreen()
}
