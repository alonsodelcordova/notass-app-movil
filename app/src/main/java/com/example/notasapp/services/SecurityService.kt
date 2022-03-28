package com.example.notasapp.services

//https://ichi.pro/es/solicitud-post-mas-simple-en-android-kotlin-usando-retrofit-247020136821018


import com.example.notasapp.models.*
import retrofit2.Call
import retrofit2.http.*

interface SecurityService {
    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(@Body user:Usuario): Call<ResLogin>

    @GET("public/universidad")
    fun getUniversidades(): Call<List<Universidad>>

    @GET("public/facultad/uni/{id}")
    fun getFacultades(@Path("id") id: String ): Call<List<Facultad>>

    @GET("public/escuela/fac/{id}")
    fun getEscuelas(@Path("id") id: String ): Call<List<Escuela>>

}