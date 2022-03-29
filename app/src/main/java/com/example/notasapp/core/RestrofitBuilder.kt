package com.example.notasapp.core

import android.content.Context
import android.content.SharedPreferences
import com.example.notasapp.models.*
import com.example.notasapp.services.SecurityService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestrofitBuilder {
    val baseUrl = "https://notas-app-jacs.herokuapp.com/api/"
    fun create(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
    //peticiones
    fun createGetUnis(): Call<List<Universidad>> {
        val  servis= create().create(SecurityService::class.java)
        return servis.getUniversidades()
    }

    fun getFacultades(id:String): Call<List<Facultad>>{
        val  servis= create().create(SecurityService::class.java)
        return servis.getFacultades(id)
    }
    fun getEscuelas(id:String): Call<List<Escuela>>{
        val  servis= create().create(SecurityService::class.java)
        return servis.getEscuelas(id)
    }

    fun login(user:Usuario): Call<ResLogin>{
        val  servis= create().create(SecurityService::class.java)
        return servis.login(user)
    }

    fun registrarEstudiante(estudiante: RegisterEstudiante):Call<Estudiante>{
        val  servis= create().create(SecurityService::class.java)
        return servis.registerEstudiante(estudiante)
    }

    // estudiante

    fun  getCursosEscuela(id:String):Call<List<Curso>>{
        val  servis= create().create(SecurityService::class.java)
        return servis.getCursosEscuela(id)
    }

    fun  getNotasEstudiante(id:String):Call<List<Nota>>{
        val  servis= create().create(SecurityService::class.java)
        return servis.getNotasEstudiante(id)
    }

    fun  registerNotaCurso(nota:RegisterNota):Call<Nota>{
        val  servis= create().create(SecurityService::class.java)
        return servis.registerNotaCurso(nota)
    }
    fun  updateNotaCurso(id:Number,nota:RegisterNota):Call<Nota>{
        val  servis= create().create(SecurityService::class.java)
        return servis.updateNotaCurso(id.toString(),nota)
    }

    fun  deleteNotaCurso(id:Number):Call<RespuestaMensaje>{
        val  servis= create().create(SecurityService::class.java)
        return servis.deleteNotaCurso(id.toString())
    }
}


