package com.example.sportsapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sportsapp.R
import com.example.sportsapp.navigation.FoodScreens
import com.example.sportsapp.ui.theme.PrimaryColor
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.ui.theme.SecondaryColor

@Composable
fun FoodScreen(navController: NavHostController = rememberNavController()) {
    Box(
        modifier = Modifier.fillMaxSize().background(PrimaryColorNavy),
        contentAlignment = Alignment.Center,
    ) {
        Column(Modifier.padding(30.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            FoodActionCardLong(
                modifier = Modifier.fillMaxWidth(),
                color = SecondaryColor,
                label = "Get a meal plan",
                icon = R.drawable.schedule,
                onClick = {
                    navController.navigate(FoodScreens.MealPlannerCategory.route)
                }
            )
            PairedCards(
                leftCard = {
                    FoodActionCard(
                        modifier = Modifier.weight(1f),
                        label = "Search Foods",
                        icon = R.drawable.baseline_search_24,
                        onClick = {
                            navController.navigate(FoodScreens.FoodSearch.route)
                        },
                    )
                },
                rightCard = {
                    FoodActionCard(
                        modifier = Modifier.weight(1f),
                        label = "Favorites",
                        icon = R.drawable.baseline_star_24,
                    )
                },
            )
        }
    }
}

@Composable
fun PairedCards(leftCard: @Composable () -> Unit = {}, rightCard: @Composable () -> Unit = {}) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        leftCard()
        rightCard()
    }
}

@Composable
fun FoodActionCard(modifier: Modifier = Modifier, label: String, color: Color = Color.White, icon: Int, onClick: () -> Unit = {}) {
    val screenSize = LocalConfiguration.current.screenWidthDp.dp
    Card(
        modifier = modifier.clickable { onClick() }.height(screenSize / 2),
        shape = RoundedCornerShape(CornerSize(20.dp)),
        backgroundColor = color,
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(modifier = Modifier.weight(3f), painter = painterResource(id = icon), contentDescription = label, contentScale = ContentScale.FillHeight)
                Text(modifier = Modifier.weight(1f), text = label, color = PrimaryColor)
            }
        }
    }
}

@Composable
fun FoodActionCardLong(modifier: Modifier = Modifier, label: String, color: Color = Color.White, icon: Int, onClick: () -> Unit = {}) {
    val screenSize = LocalConfiguration.current.screenWidthDp.dp
    Card(
        modifier = modifier.clickable { onClick() }.height(screenSize / 2),
        shape = RoundedCornerShape(CornerSize(20.dp)),
        backgroundColor = color,
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.TopStart) {
            Row(modifier = Modifier.fillMaxHeight()) {
                Image(modifier = Modifier.align(Alignment.CenterVertically).height(screenSize / 4).width(screenSize / 4), painter = painterResource(id = icon), contentDescription = label, contentScale = ContentScale.FillBounds)
                Text(modifier = Modifier, text = label, color = PrimaryColor, fontSize = 35.sp, textAlign = TextAlign.End, overflow = TextOverflow.Clip)
            }
        }
    }
}

@Preview
@Composable
fun prev() {
    FoodScreen()
}
