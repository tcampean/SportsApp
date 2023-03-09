package com.example.sportsapp.api

import com.example.sportsapp.data.Results
import retrofit2.Call
import retrofit2.http.GET

interface ISpoonacularAPI {
    @GET("/recipes/complexSearch?apiKey=52324220637546daa5cc71f978daebf2")
    fun getRecipes(): Call<Results>
}
