package com.example.gestionalmacen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActualizarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar)

        // 1. Referencias de la interfaz
        val etId = findViewById<EditText>(R.id.etId)
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etPrecio = findViewById<EditText>(R.id.etPrecio)
        val btnActualizar = findViewById<Button>(R.id.btnActualizar)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)
        val btnBuscar = findViewById<Button>(R.id.btnBuscar)

        // --- 2. RECIBIR DATOS DEL INTENT (Desde la lista) ---
        val idRecibido = intent.getIntExtra("id", 0)
        if (idRecibido != 0) {
            etId.setText(idRecibido.toString())
            etNombre.setText(intent.getStringExtra("nombre"))
            etPrecio.setText(intent.getDoubleExtra("precio", 0.0).toString())
            etId.isEnabled = false
        }

        // --- 3. LÓGICA PARA BUSCAR PRODUCTO POR ID ---
        btnBuscar.setOnClickListener {
            val idStr = etId.text.toString()
            if (idStr.isEmpty()) {
                Toast.makeText(this, "Ingresa un ID para buscar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            RetrofitClient.instance.buscarProducto(idStr.toInt()).enqueue(object : Callback<Producto> {
                override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                    if (response.isSuccessful && response.body() != null) {
                        val producto = response.body()!!
                        etNombre.setText(producto.nombre)
                        etPrecio.setText(producto.precio.toString())
                        Toast.makeText(this@ActualizarActivity, "Producto cargado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ActualizarActivity, "Producto no encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Producto>, t: Throwable) {
                    Toast.makeText(this@ActualizarActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // --- 4. LÓGICA PARA ACTUALIZAR ---
        btnActualizar.setOnClickListener {
            val id = etId.text.toString().toIntOrNull() ?: 0
            val nombre = etNombre.text.toString()
            val precio = etPrecio.text.toString().toDoubleOrNull() ?: 0.0

            if (id == 0 || nombre.isEmpty()) {
                Toast.makeText(this, "ID y Nombre son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            RetrofitClient.instance.actualizarProducto(id, nombre, precio).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ActualizarActivity, "¡Actualizado!", Toast.LENGTH_SHORT).show()
                        irAlMenuPrincipal() // <--- CAMBIO AQUÍ
                    }
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(this@ActualizarActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // --- 5. LÓGICA PARA ELIMINAR ---
        btnEliminar.setOnClickListener {
            val id = etId.text.toString().toIntOrNull() ?: 0
            if (id == 0) return@setOnClickListener

            AlertDialog.Builder(this)
                .setTitle("Eliminar Producto")
                .setMessage("¿Estás seguro de eliminar este producto?")
                .setPositiveButton("Sí, Eliminar") { _, _ ->
                    RetrofitClient.instance.eliminarProducto(id).enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@ActualizarActivity, "Eliminado", Toast.LENGTH_SHORT).show()
                                irAlMenuPrincipal() // <--- CAMBIO AQUÍ
                            }
                        }
                        override fun onFailure(call: Call<String>, t: Throwable) {}
                    })
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }

    // --- FUNCIÓN PARA LIMPIAR LA PILA Y VOLVER AL INICIO ---
    private fun irAlMenuPrincipal() {
        val intent = Intent(this, MainActivity::class.java)
        // Esto cierra VerProductosActivity y cualquier otra que esté abierta
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun limpiarCampos(vararg editTexts: EditText) {
        for (et in editTexts) {
            et.text.clear()
        }
    }
}