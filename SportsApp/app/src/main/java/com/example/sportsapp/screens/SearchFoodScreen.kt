package com.example.sportsapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.R
import com.example.sportsapp.components.*
import com.example.sportsapp.data.Recipe
import com.example.sportsapp.navigation.FoodScreens
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.viewmodels.SearchViewModel

@Composable
fun SearchFoodScreen(navController: NavController = rememberNavController(), viewModel: SearchViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val resultList by viewModel.recipeList.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize().background(PrimaryColorNavy),
        contentAlignment = Alignment.Center,
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(20.dp)) {
            SearchBar(
                searchText = searchText,
                onTextChange = {
                    viewModel.setSearchText(it)
                },
                onSearchButtonClicked = {
                    viewModel.searchRecipe()
                },
            )

            PaginatedRecipeLazyColumn(
                resultList,
                itemLayout = {
                    SearchFoodItem(title = (it as Recipe).title, imageUrl = it.image, onClick = {
                        navController.currentBackStackEntry?.arguments?.putInt("ID", it.id)
                        navController.navigate(route = FoodScreens.FoodDetails.route)
                    })
                },
                onScrollToEnd = {
                    viewModel.requestDataAppend()
                },
            )
        }
    }
}

@Composable
fun SearchBar(searchText: String, onTextChange: (String) -> Unit = {}, onSearchButtonClicked: () -> Unit = {}, onFilterResultsClicked: () -> Unit = {}) {
    val screenSize = LocalConfiguration.current.screenHeightDp.dp
    RoundBottomCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color.White,
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.CenterVertically) {
            ImageButton(
                modifier = Modifier.size(screenSize.div(22)),
                image = painterResource(id = R.drawable.baseline_search_24),
                onClick = { onSearchButtonClicked() },
            )

            SearchBarTextField(
                modifier = Modifier.weight(1f),
                value = searchText,
                label = "Search foods...",
                textStyle = TextStyle.Default,
                onValueChange = { onTextChange(it) },
            )
        }
    }
}

@Preview
@Composable
fun PreviewSearchScreen() {
    SearchFoodScreen()
}
