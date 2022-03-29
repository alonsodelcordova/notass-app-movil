package com.example.notasapp.services

//https://ichi.pro/es/solicitud-post-mas-simple-en-android-kotlin-usando-retrofit-247020136821018


import com.example.notasapp.models.*
import retrofit2.Call
import retrofit2.http.*

interface SecurityService {
    @POST("public/login")
    fun login(@Body user:Usuario): Call<ResLogin>

    @GET("public/universidad")
    fun getUniversidades(): Call<List<Universidad>>

    @GET("public/facultad/uni/{id}")
    fun getFacultades(@Path("id") id: String ): Call<List<Facultad>>

    @GET("public/escuela/fac/{id}")
    fun getEscuelas(@Path("id") id: String ): Call<List<Escuela>>

    @POST("public/register")
    fun registerEstudiante(@Body estudiante:RegisterEstudiante ): Call<Estudiante>

    // estudiante
    @GET("estudiante/curso/{id}")
    fun getCursosEscuela(@Path("id") id: String ): Call<List<Curso>>

    @GET("estudiante/nota/estudiante/{id}")
    fun getNotasEstudiante(@Path("id") id: String ): Call<List<Nota>>

    @POST("estudiante/nota")
    fun registerNotaCurso(@Body nota:RegisterNota ): Call<Nota>

    @PUT("estudiante/nota/{id}")
    fun updateNotaCurso(@Path("id") id: String,@Body nota:RegisterNota ): Call<Nota>

    @DELETE("estudiante/nota/{id}")
    fun deleteNotaCurso(@Path("id") id: String): Call<RespuestaMensaje>
}