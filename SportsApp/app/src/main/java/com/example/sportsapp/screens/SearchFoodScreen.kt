package com.example.sportsapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.sportsapp.api.SpoonacularAPI
import com.example.sportsapp.data.Results
import com.example.sportsapp.ui.theme.PrimaryColorNavy
import retrofit2.await

@Composable
fun SearchFoodScreen() {
    var result1 by remember { mutableStateOf<Results>(Results(listOf(), 0, 0, 0)) }
    LaunchedEffect(key1 = "a", block = {
        val result = SpoonacularAPI.retrofitService.getRecipes()
        result1 = result.await()
        Log.d("wowowoow", result1.results.toString())
    })
    Box(
        modifier = Modifier.fillMaxSize().background(PrimaryColorNavy),
        contentAlignment = Alignment.Center,
    ) {
        LazyColumn {
            items(items = result1.results) {
                Text(text = it.title)
            }
        }
    }
}
