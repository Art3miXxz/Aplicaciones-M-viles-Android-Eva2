package com.example.sensor_gas

import java.io.Serializable

data class Usuario(
    val rut: String,
    val nombre: String,
    val apellido: String,
    val email: String,
    val tipo: String,
    val contraseña: String
) : Serializable