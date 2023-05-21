package com.example.sportsapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sportsapp.data.Recipe
import com.example.sportsapp.navigation.FoodScreens
import com.example.sportsapp.screens.SearchFoodItem

@Composable
fun PaginatedRecipeLazyColumn(navController: NavController, itemList: List<Recipe>, onScrollToEnd: () -> Unit) {
    val listState = rememberLazyListState()
    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), state = listState) {
        items(items = itemList) {
            SearchFoodItem(title = it.title, imageUrl = it.image, onClick = {
                navController.currentBackStackEntry?.arguments?.putInt("ID", it.id)
                navController.navigate(route = FoodScreens.FoodDetails.route)
            })
        }

        if (listState.isScrolledToTheEnd()) {
            onScrollToEnd()
        }
    }
}

fun LazyListState.isScrolledToTheEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
