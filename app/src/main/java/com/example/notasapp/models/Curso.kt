package com.example.notasapp.models

import java.io.Serializable

class Curso(
    val id:Number,
    val nombre:String,
    val tipo:String,
    val ciclo:Number,
    val creditos:Number,
    val escuela_id: Number,
    val descripcion:String
): Serializable {
}