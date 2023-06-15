package com.example.sportsapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MainAPI {
    private const val BASE_URL = "http://10.0.2.2:5000"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
    val retrofitService: IMainAPI by lazy {
        retrofit.create(IMainAPI::class.java)
    }
}