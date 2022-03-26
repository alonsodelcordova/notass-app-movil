package com.example.notasapp.models

import com.google.gson.annotations.SerializedName

data class Universidad(
    @SerializedName("id") var id:Int,
    @SerializedName("nombre") var nombre:String
){

}

data class Facultad(
  @SerializedName("id") var id:Int,
  @SerializedName("nombre") var nombre:String,
){}

data class Escuela(
    @SerializedName("id") var id:Int,
    @SerializedName("nombre") var nombre:String,
    @SerializedName("codigo") var codigo:String,
    @SerializedName("cre_obli") var cre_obli:Number,
    @SerializedName("cre_ele") var cre_ele:Number,
    @SerializedName("ciclos") var ciclos: Number
){}
