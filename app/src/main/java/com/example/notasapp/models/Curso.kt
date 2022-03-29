package com.example.notasapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Curso(
    @SerializedName("id") val id:Number,
    @SerializedName("nombre") val nombre:String,
    @SerializedName("tipo") val tipo:String,
    @SerializedName("ciclo") val ciclo:Number,
    @SerializedName("creditos") val creditos:Number,
    @SerializedName("escuela_id") val escuela_id: Number,
    var nota:Int?,
    var nota_id:Int?,
) {

}

data class Nota(
    @SerializedName("id") val id :Number,
    @SerializedName("curso_id") var curso_id:Number,
    @SerializedName("estudiante_id") val estudiante_id:Number,
    @SerializedName("nota") val nota:Int
) {

}

data class RegisterNota(
    @SerializedName("curso_id") var curso_id:Number,
    @SerializedName("estudiante_id") val estudiante_id:Number,
    @SerializedName("nota") val nota:Int
) {

}

