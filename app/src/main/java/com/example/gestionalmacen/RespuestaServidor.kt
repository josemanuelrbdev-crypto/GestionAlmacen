package com.example.gestionalmacen

import androidx.annotation.Keep

@Keep
data class RespuestaServidor(
    val estado: String?,
    val mensaje: String?,
    val error: String?
)