package com.example.notasapp.models

import java.io.Serializable

class Estudiante(
    val id:Number,
    val codigo:String,
    val nombres:String,
    val apellidos:String,
    val direccion :String,
    val dni:String
): Serializable
{}