package com.example.evair.produtos.api

import com.example.evair.produtos.model.Login
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {


    @POST("/login/authenticate")
    fun login(@Body login: Login): Call<Login>

    @POST("/login")
    fun salvar(@Body login: Login): Call<Void>

}