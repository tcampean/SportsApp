package com.example.sportsapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SpoonacularAPI {
    private const val BASE_URL = "https://api.spoonacular.com"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
    val retrofitService: ISpoonacularAPI by lazy {
        retrofit.create(ISpoonacularAPI::class.java)
    }
}
