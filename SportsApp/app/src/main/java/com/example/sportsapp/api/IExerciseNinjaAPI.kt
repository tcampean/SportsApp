package com.example.sportsapp.api

import com.example.sportsapp.data.Exercise
import com.example.sportsapp.data.Results
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


//VKhgPIa9Nc4ErWYpp3jE45XRbNIbi6dA0ueFlQv3 EXERCISES
interface IExerciseNinjaAPI {

    @GET("/exercises?X-Api-Key=VKhgPIa9Nc4ErWYpp3jE45XRbNIbi6dA0ueFlQv3")
    fun getExercises(@Query("muscle") query: String, @Query("difficulty") offset: Int): Call<List<Exercise>>
}