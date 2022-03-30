package com.example.notasapp.models

import com.google.gson.annotations.SerializedName

data class Estudiante(
    @SerializedName("id") val id:Number,
    @SerializedName("codigo") val codigo:String,
    @SerializedName("nombres") val nombres:String,
    @SerializedName("apellidos") val apellidos:String,
    @SerializedName("direccion") val direccion :String,
    @SerializedName("dni") val dni:String,
    @SerializedName("escuela_id")val escuela_id:Number,
    @SerializedName("escuela")val escuela:Escuela
)
{}

data class DatosEstudiante(
    @SerializedName("escuela") var escuela:String,
    @SerializedName("facultad") var facultad:String,
    @SerializedName("universidad") var universidad:String
){

}