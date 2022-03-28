package com.example.notasapp.services

//https://ichi.pro/es/solicitud-post-mas-simple-en-android-kotlin-usando-retrofit-247020136821018


import com.example.notasapp.models.*
import retrofit2.Call
import retrofit2.http.*

interface SecurityService {
    @Headers("Content-Type: application/json")
    @POST("public/login")
    fun login(@Body user:Usuario): Call<ResLogin>

    @Headers("Content-Type: application/json")
    @GET("public/universidad")
    fun getUniversidades(): Call<List<Universidad>>

    @Headers("Content-Type: application/json")
    @GET("public/facultad/uni/{id}")
    fun getFacultades(@Path("id") id: String ): Call<List<Facultad>>

    @Headers("Content-Type: application/json")
    @GET("public/escuela/fac/{id}")
    fun getEscuelas(@Path("id") id: String ): Call<List<Escuela>>

}