package com.example.sportsapp.api

import com.example.sportsapp.data.requestData.LoginData
import com.example.sportsapp.data.requestData.RegisterData
import com.example.sportsapp.data.requestData.UserDetails
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface IMainAPI {

    @GET("/login/")
    fun login(@Query("username") username: String, @Query("password") password: String): Call<UserDetails>

    @POST("/register/")
    fun register(@Body registerData: RegisterData): Call<ResponseBody>

    @PUT("/updateWeight/")
    fun updateWeight(@Query("username") username: String, @Query("weight") weight: Int): Call<ResponseBody>
}
