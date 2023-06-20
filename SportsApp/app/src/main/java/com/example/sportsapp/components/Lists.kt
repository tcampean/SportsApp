package com.example.sportsapp.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sportsapp.R
import com.example.sportsapp.data.Exercise
import com.example.sportsapp.data.Recipe
import com.example.sportsapp.navigation.FoodScreens
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

@Composable
fun PaginatedRecipeLazyColumn(itemList: List<Any>, itemLayout: @Composable (item: Any) -> Unit, onScrollToEnd: () -> Unit) {
    val listState = rememberLazyListState()
    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), state = listState) {
        items(items = itemList) {
            itemLayout(it)
        }

        if (listState.isScrolledToTheEnd()) {
            onScrollToEnd()
        }
    }
}

@Composable
fun SearchFoodItem(title: String, imageUrl: String, onClick: () -> Unit) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    val picasso = remember { Picasso.get() }
    val context = LocalContext.current
    LaunchedEffect(imageUrl) {
        launch(Dispatchers.IO) {
            try {
                val bitmap = picasso.load(imageUrl).get()
                imageBitmap = bitmap.asImageBitmap()
            } catch (e: SocketTimeoutException) {
                imageBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_food).asImageBitmap()
            }
        }
    }

    val screenSize = LocalConfiguration.current.screenHeightDp.dp
    BaseCard(
        modifier = Modifier.fillMaxWidth().height(screenSize.div(4)).clickable {
            onClick()
        },
        cornerSize = 0,
        backgroundColor = Color.White,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            imageBitmap?.let {
                Image(modifier = Modifier.fillMaxWidth().weight(4f), bitmap = imageBitmap!!, contentDescription = "$title image", contentScale = ContentScale.FillBounds)
            }
            Box(modifier = Modifier.weight(2f), contentAlignment = Alignment.Center) {
                Text(
                    modifier = Modifier.padding(
                        top = 5.dp,
                        start = 10.dp,
                        end = 5.dp,
                        bottom = 5.dp,
                    ).align(Alignment.Center),
                    text = title,
                    color = PrimaryColorNavy,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    overflow = TextOverflow.Visible,
                )
            }
        }
    }
}

@Composable
fun SearchFoodItemNoPicture(title: String, onClick: () -> Unit, onDeleteClick: () -> Unit) {
    BaseCard(
        modifier = Modifier.fillMaxWidth().clickable {
            onClick()
        },
        cornerSize = 0,
        backgroundColor = Color.White,
    ) {
        Row {
            Box(modifier = Modifier.weight(5f), contentAlignment = Alignment.Center) {
                Text(
                    modifier = Modifier.padding(
                        top = 5.dp,
                        start = 10.dp,
                        end = 5.dp,
                        bottom = 5.dp,
                    ).align(Alignment.Center),
                    text = title,
                    color = PrimaryColorNavy,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    overflow = TextOverflow.Visible,
                )
            }

            Image(modifier = Modifier.size(30.dp).align(Alignment.CenterVertically).weight(1f)
                .clickable { onDeleteClick() }, painter = painterResource(id = R.drawable.baseline_delete_24), contentDescription = "delete")
        }
    }
}

fun LazyListState.isScrolledToTheEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

@Composable
fun PaginatedExerciseLazyColumn(navController: NavController, itemList: List<Exercise>, onScrollToEnd: () -> Unit) {
    val listState = rememberLazyListState()
    LazyColumn(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp), state = listState) {
        items(items = itemList) {
            ExerciseItem(exercise = it, onClick = {
                navController.navigate(route = FoodScreens.FoodDetails.route)
            })
        }

        if (listState.isScrolledToTheEnd()) {
            onScrollToEnd()
        }
    }
}

@Composable
fun ExerciseItem(exercise: Exercise, onClick: () -> Unit) {
    val screenSize = LocalConfiguration.current.screenHeightDp.dp
    BaseCard(
        modifier = Modifier.fillMaxWidth().clickable {
            onClick()
        },
        cornerSize = 0,
        backgroundColor = Color.White,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.padding(
                    top = 5.dp,
                    start = 10.dp,
                    end = 5.dp,
                    bottom = 5.dp,
                ),
                text = exercise.name,
                color = PrimaryColorNavy,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                overflow = TextOverflow.Visible,
            )
            Text(
                modifier = Modifier.padding(
                    top = 5.dp,
                    start = 10.dp,
                    end = 5.dp,
                    bottom = 5.dp,
                ),
                text = "Type: " + exercise.type,
                color = PrimaryColorNavy,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                overflow = TextOverflow.Visible,
            )
        }
    }
}
