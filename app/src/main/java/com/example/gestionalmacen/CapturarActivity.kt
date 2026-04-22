package com.example.gestionalmacen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CapturarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capturar)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etPrecio = findViewById<EditText>(R.id.etPrecio)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        // --- 1. GUARDAR NUEVO PRODUCTO ---
        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val precioStr = etPrecio.text.toString().trim()

            // Validación simple antes de enviar
            if (nombre.isEmpty() || precioStr.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val precio = precioStr.toDoubleOrNull() ?: 0.0

            RetrofitClient.instance.insertarProducto(nombre, precio).enqueue(object : Callback<RespuestaServidor> {
                override fun onResponse(call: Call<RespuestaServidor>, response: Response<RespuestaServidor>) {
                    val res = response.body()

                    if (response.isSuccessful && res?.estado == "success") {
                        // ÉXITO: Mostramos mensaje y volvemos al menú
                        Toast.makeText(this@CapturarActivity, "¡Producto guardado!", Toast.LENGTH_SHORT).show()
                        irAlMenuPrincipal()
                    } else {
                        // ERROR DE SERVIDOR: (Ej: nombre duplicado o error SQL)
                        val errorMsg = res?.mensaje ?: "Error al guardar el producto"
                        Toast.makeText(this@CapturarActivity, errorMsg, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RespuestaServidor>, t: Throwable) {
                    // ERROR DE RED: (Ej: No hay internet o servidor caído)
                    Toast.makeText(this@CapturarActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    // --- 2. NAVEGACIÓN: VOLVER AL MENÚ LIMPIANDO LA PILA ---
    private fun irAlMenuPrincipal() {
        val intent = Intent(this, MainActivity::class.java)
        // FLAG_ACTIVITY_CLEAR_TOP evita que el usuario regrese a la pantalla de captura al dar "atrás"
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}