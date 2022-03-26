package com.example.notasapp.core

import com.example.notasapp.models.Universidad
import com.example.notasapp.services.SecurityService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    fun createGetUnis(): Call<List<Universidad>> {
        val  servis= create().create(SecurityService::class.java)
        return servis.getUniversidades()
    }

}


