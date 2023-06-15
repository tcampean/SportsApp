package com.example.sportsapp.api

import okhttp3.Request
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface IMainAPI {

    @GET("/login")
    fun login(): Call<List<Response>>

    @POST("/register")
    fun register(@Body request: Request): Call<Response>

    @PUT("/updateWeight")
    fun updateWeight(@Body body: Response): Call<Response>
}
