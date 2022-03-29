package com.example.notasapp.models

import com.google.gson.annotations.SerializedName

data class Usuario (
    @SerializedName("name") val name:String,
    @SerializedName("password") val password:String
){
}

data class UsuarioRes (
    @SerializedName("id") val id:String?="",
    @SerializedName("name") val name:String,
    @SerializedName("password") val password:String,
    @SerializedName("tipo") val tipo:String?=""
){
}


data class ResLogin(
    @SerializedName("user") val user:UsuarioRes,
    @SerializedName("estudiante") val estudiante: Estudiante?,
){

}

data class RegisterEstudiante(
    @SerializedName("apellidos") val apellidos:String,
    @SerializedName("nombres") val nombres:String,
    @SerializedName("codigo") val codigo:String,
    @SerializedName("direccion") val direccion:String,
    @SerializedName("dni") val dni:String,
    @SerializedName("email") val email:String,
    @SerializedName("escuela_id") val escuela_id:String,
    )

