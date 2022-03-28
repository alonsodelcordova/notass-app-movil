package com.example.notasapp.models

import com.google.gson.annotations.SerializedName

data class Usuario (
    @SerializedName("id") val id:String?,
    @SerializedName("username") val username:String,
    @SerializedName("password") val password:String,
    @SerializedName("token") val token:String?,
    @SerializedName("tipo") val tipo:String?
){
}

data class ResLogin(
    @SerializedName("user") val user:Usuario,
    @SerializedName("estudiante") val estudiante: Estudiante?,
){

}

