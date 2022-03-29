package com.example.notasapp.models

import com.google.gson.annotations.SerializedName

data class Estudiante(
    @SerializedName("id") val id:Number,
    @SerializedName("codigo") val codigo:String,
    @SerializedName("nombres") val nombres:String,
    @SerializedName("apellidos") val apellidos:String,
    @SerializedName("direccion") val direccion :String,
    @SerializedName("dni") val dni:String,
    @SerializedName("escuela_id")val escuela_id:Number
)
{}