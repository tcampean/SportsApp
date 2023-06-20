package com.example.sportsapp.api

import com.example.sportsapp.data.requestData.RegisterData
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface IMainAPI {

    @GET("/login")
    fun login(): Call<List<Response>>

    @POST("/register/")
    fun register(@Body registerData: RegisterData): Call<ResponseBody>

    @PUT("/updateWeight")
    fun updateWeight(@Body body: Response): Call<Response>
}
