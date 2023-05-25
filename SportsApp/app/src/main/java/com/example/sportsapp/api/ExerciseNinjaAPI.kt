package com.example.sportsapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ExerciseNinjaAPI {
    private const val BASE_URL = "https://api.api-ninjas.com/v1"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
    val retrofitService: IExerciseNinjaAPI by lazy {
        retrofit.create(IExerciseNinjaAPI::class.java)
    }
}