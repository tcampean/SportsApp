package com.example.sportsapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiKey = "L6IlWHXrG97B2tHAlogqKsrlIJwf3k0gPI3a5djd"

object ExerciseNinjaAPI {
    val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("x-api-key", apiKey)
        val newRequest = requestBuilder.build()
        chain.proceed(newRequest)
    }
    private const val BASE_URL = "https://api.api-ninjas.com"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
        .baseUrl(BASE_URL)
        .build()
    val retrofitService: IExerciseNinjaAPI by lazy {
        retrofit.create(IExerciseNinjaAPI::class.java)
    }
}
