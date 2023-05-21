package com.example.sportsapp.api

import com.example.sportsapp.data.RecipeDetailed
import com.example.sportsapp.data.Results
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ISpoonacularAPI {
    @GET("/recipes/complexSearch?apiKey=9f40e23d18ad417eb2f95b96c1893e4f&number=10")
    fun getRecipes(@Query("query") query: String, @Query("offset") offset: Int): Call<Results>

    @GET("/recipes/{id}/information?apiKey=9f40e23d18ad417eb2f95b96c1893e4f&includeNutrition=true")
    fun getRecipeDetails(@Path("id") id: Int): Call<RecipeDetailed>

    https://api.spoonacular.com/mealplanner/generate?timeFrame=day
}
