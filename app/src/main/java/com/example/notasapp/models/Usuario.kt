package com.example.notasapp.models

import com.google.gson.annotations.SerializedName

data class Usuario (
    @SerializedName("username") val username:String,
    @SerializedName("password") val password:String,
    @SerializedName("token") val token:String?
        ){
}