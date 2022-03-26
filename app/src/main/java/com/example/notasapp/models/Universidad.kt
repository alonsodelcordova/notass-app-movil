package com.example.notasapp.models

import com.google.gson.annotations.SerializedName

data class Universidad(
    @SerializedName("id") var id:Int,
    @SerializedName("nombre") var nombre:String
){

}
