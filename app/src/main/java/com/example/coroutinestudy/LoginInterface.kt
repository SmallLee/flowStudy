package com.example.coroutinestudy

import com.example.coroutinestudy.http.retrofit
import retrofit2.http.GET


val Api: LoginInterface by lazy {
    retrofit.create(LoginInterface::class.java)
}

interface LoginInterface {
    @GET("/article/listproject/0/json")
    suspend fun getListProject(): BaseResponse<String?>

    @GET("/article/listproject/0/json")
    suspend fun getListProjec1t(): BaseResponse<String?>
}