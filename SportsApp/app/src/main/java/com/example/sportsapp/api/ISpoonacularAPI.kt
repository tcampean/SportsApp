package com.example.sportsapp.api

import com.example.sportsapp.data.DayPlan
import com.example.sportsapp.data.RecipeDetailed
import com.example.sportsapp.data.Results
import com.example.sportsapp.data.WeekPlan
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//SPOONACULAR
//52324220637546daa5cc71f978daebf2
//9f40e23d18ad417eb2f95b96c1893e4f

interface ISpoonacularAPI {
    @GET("/recipes/complexSearch?apiKey=9f40e23d18ad417eb2f95b96c1893e4f&number=10")
    fun getRecipes(@Query("query") query: String, @Query("offset") offset: Int): Call<Results>

    @GET("/recipes/{id}/information?apiKey=9f40e23d18ad417eb2f95b96c1893e4f&includeNutrition=true")
    fun getRecipeDetails(@Path("id") id: Int): Call<RecipeDetailed>

    @GET("mealplanner/generate?apiKey=9f40e23d18ad417eb2f95b96c1893e4f&timeFrame=day")
    fun generateDayMeal(@Query("diet") diet: String, @Query("targetCalories") targetCalories: Int): Call<DayPlan>

    @GET("mealplanner/generate?apiKey=9f40e23d18ad417eb2f95b96c1893e4f&timeFrame=week")
    fun generateWeekMeal(@Query("diet") diet: String, @Query("targetCalories") targetCalories: Int): Call<WeekPlan>
}
