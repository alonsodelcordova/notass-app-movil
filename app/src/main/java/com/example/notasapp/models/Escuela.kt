package com.example.notasapp.models

import java.io.Serializable


class Escuela(
    val id:Number,
    val nombre:String,
    val codigo:String,
    val cre_obli:Number,
    val cre_ele:Number,
    val ciclos: Number,
    val facultad_id:Number
): Serializable {
}