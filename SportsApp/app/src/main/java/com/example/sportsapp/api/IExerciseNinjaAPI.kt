package com.example.sportsapp.api

import com.example.sportsapp.data.Exercise
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

// VKhgPIa9Nc4ErWYpp3jE45XRbNIbi6dA0ueFlQv3 EXERCISES
interface IExerciseNinjaAPI {

    @GET("/v1/exercises")
    fun getExercises(
        @Header("x-api-key") apiKey: String = "L6IlWHXrG97B2tHAlogqKsrlIJwf3k0gPI3a5djd",
        @Query("muscle") muscle: String,
        @Query("offset") offset: Int,
    ): Call<List<Exercise>>

    @GET("/v1/exercises")
    fun getExercisesByName(
        @Header("x-api-key") apiKey: String = "L6IlWHXrG97B2tHAlogqKsrlIJwf3k0gPI3a5djd",
        @Query("name") name: String,
        @Query("offset") offset: Int,
    ): Call<List<Exercise>>
}
