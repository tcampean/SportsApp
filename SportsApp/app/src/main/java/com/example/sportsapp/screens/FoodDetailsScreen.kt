package com.example.sportsapp.screens

import android.text.Html
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportsapp.components.RoundTopCard
import com.example.sportsapp.data.Ingredient
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.example.sportsapp.ui.theme.SecondaryColor
import com.example.sportsapp.viewmodels.FoodDetailsViewModel

@Composable
fun FoodDetailsScreen(viewModel: FoodDetailsViewModel) {
    val image by viewModel.image.collectAsState()
    val title by viewModel.title.collectAsState()
    val serving by viewModel.servings.collectAsState()
    val readyInMinutes by viewModel.readyInMinutes.collectAsState()
    val shouldDisplayProgressBar by viewModel.shouldDisplayProgressBar.collectAsState()
    val extendedIngredients by viewModel.extendedIngredients.collectAsState()
    val summary by viewModel.summary.collectAsState()
    val calories by viewModel.calories.collectAsState()
    val protein by viewModel.protein.collectAsState()
    val fat by viewModel.fat.collectAsState()
    val carbs by viewModel.carbs.collectAsState()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    LaunchedEffect(Unit) {
        viewModel.requestRecipeDetails()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColorNavy)
            .verticalScroll(rememberScrollState()),
    ) {
        if (shouldDisplayProgressBar) {
            CircularProgressIndicator(Modifier.size(screenHeight / 8).align(Alignment.Center), color = SecondaryColor)
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight / 4),
                    bitmap = image,
                    contentDescription = "food image",
                    contentScale = ContentScale.FillBounds,
                )
                Spacer(modifier = Modifier.size(20.dp))

                RoundTopCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color.White) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = title,
                            color = PrimaryColorNavy,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                        )

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Ready In Minutes: $readyInMinutes",
                            color = PrimaryColorNavy,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                        )

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Servings: $serving",
                            color = PrimaryColorNavy,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Light,
                        )

                        Spacer(modifier = Modifier.size(10.dp))

                        NutritionSection(
                            modifier = Modifier.fillMaxWidth(),
                            calories = calories,
                            proteins = protein,
                            fats = fat,
                            carbs = carbs,
                        )

                        Spacer(modifier = Modifier.size(10.dp))

                        SummarySection(modifier = Modifier.fillMaxWidth(), text = summary)

                        Spacer(modifier = Modifier.size(10.dp))

                        IngredientsSection(modifier = Modifier.fillMaxWidth(), ingredients = extendedIngredients)
                    }
                }
            }
        }
    }
}

@Composable
fun SummarySection(modifier: Modifier, text: String) {
    Column(modifier = modifier) {
        Text(
            text = "Summary",
            fontSize = 22.sp,
            color = PrimaryColorNavy,
            fontWeight = FontWeight.SemiBold,
        )

        Text(
            text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT).toString(),
            fontSize = 18.sp,
            color = PrimaryColorNavy,
            fontWeight = FontWeight.Light,
        )
    }
}

@Composable
fun IngredientsSection(modifier: Modifier, ingredients: List<Ingredient>) {
    Column(modifier = modifier) {
        Text(
            text = "Ingredients",
            fontSize = 22.sp,
            color = PrimaryColorNavy,
            fontWeight = FontWeight.SemiBold,
        )

        for (ingredient in ingredients) {
            Text(
                text = ingredient.original,
                color = PrimaryColorNavy,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
            )
        }
    }
}

@Composable
fun NutritionSection(modifier: Modifier, calories: Float, proteins: Float, fats: Float, carbs: Float) {
    Column(modifier = modifier) {
        Row {
            Text(
                text = "Calories: ",
                fontSize = 22.sp,
                color = PrimaryColorNavy,
                fontWeight = FontWeight.SemiBold,
            )

            Text(
                text = "$calories kcal",
                fontSize = 22.sp,
                color = PrimaryColorNavy,
                fontWeight =FontWeight.Light,
            )
        }

        Row {
            Text(
                text = "Proteins: ",
                fontSize = 22.sp,
                color = PrimaryColorNavy,
                fontWeight = FontWeight.SemiBold,
            )

            Text(
                text = "$proteins g",
                fontSize = 22.sp,
                color = PrimaryColorNavy,
                fontWeight = FontWeight.Light,
            )
        }

        Row {
            Text(
                text = "Fats: ",
                fontSize = 22.sp,
                color = PrimaryColorNavy,
                fontWeight = FontWeight.SemiBold,
            )

            Text(
                text = "$fats g",
                fontSize = 22.sp,
                color = PrimaryColorNavy,
                fontWeight = FontWeight.Light,
            )
        }

        Row {
            Text(
                text = "Carbohydrates: ",
                fontSize = 22.sp,
                color = PrimaryColorNavy,
                fontWeight = FontWeight.SemiBold,
            )

            Text(
                text = "$carbs g",
                fontSize = 22.sp,
                color = PrimaryColorNavy,
                fontWeight = FontWeight.Light,
            )
        }
    }
}

@Preview
@Composable
fun PreviewFoodDetailsScreen() {
    val viewModel: FoodDetailsViewModel = viewModel()
    FoodDetailsScreen(viewModel = viewModel)
}
