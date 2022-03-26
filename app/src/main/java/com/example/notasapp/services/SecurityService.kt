package com.example.notasapp.services

//https://ichi.pro/es/solicitud-post-mas-simple-en-android-kotlin-usando-retrofit-247020136821018


import com.example.notasapp.models.Universidad
import com.example.notasapp.models.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Headers

interface SecurityService {
    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(@Body user:Usuario): Call<Usuario>

    @GET("public/universidad")
    fun getUniversidades(): Call<List<Universidad>>

}