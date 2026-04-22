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

        val etId = findViewById<EditText>(R.id.etId)
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etPrecio = findViewById<EditText>(R.id.etPrecio)
        val btnActualizar = findViewById<Button>(R.id.btnActualizar)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)
        val btnBuscar = findViewById<Button>(R.id.btnBuscar)

        // --- 1. ESTADO INICIAL: BOTONES DESACTIVADOS ---
        btnActualizar.isEnabled = false
        btnEliminar.isEnabled = false

        // --- 2. RECIBIR DATOS DEL INTENT (Si viene de la lista, ya existen, así que activamos) ---
        val idRecibido = intent.getIntExtra("id", 0)
        if (idRecibido != 0) {
            etId.setText(idRecibido.toString())
            etNombre.setText(intent.getStringExtra("nombre"))
            etPrecio.setText(intent.getDoubleExtra("precio", 0.0).toString())
            etId.isEnabled = false

            // Si viene de la lista, el producto existe: activamos botones
            btnActualizar.isEnabled = true
            btnEliminar.isEnabled = true
        }

        // --- 3. BUSCAR PRODUCTO ---
        btnBuscar.setOnClickListener {
            val idStr = etId.text.toString()
            if (idStr.isEmpty()) return@setOnClickListener

            RetrofitClient.instance.buscarProducto(idStr.toInt()).enqueue(object : Callback<Producto> {
                override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                    val producto = response.body()

                    if (response.isSuccessful && producto?.nombre != null) {
                        etNombre.setText(producto.nombre)
                        etPrecio.setText(producto.precio.toString())

                        // ¡PRODUCTO ENCONTRADO! Activamos botones
                        btnActualizar.isEnabled = true
                        btnEliminar.isEnabled = true

                        Toast.makeText(this@ActualizarActivity, "Cargado", Toast.LENGTH_SHORT).show()
                    } else {
                        // PRODUCTO NO EXISTE: Limpiamos y desactivamos botones
                        Toast.makeText(this@ActualizarActivity, "Producto no existe", Toast.LENGTH_SHORT).show()
                        limpiarCampos(etNombre, etPrecio)

                        btnActualizar.isEnabled = false
                        btnEliminar.isEnabled = false
                    }
                }
                override fun onFailure(call: Call<Producto>, t: Throwable) {
                    Toast.makeText(this@ActualizarActivity, "Error de red", Toast.LENGTH_SHORT).show()
                    btnActualizar.isEnabled = false
                    btnEliminar.isEnabled = false
                }
            })
        }

        // --- 4. ACTUALIZAR ---
        btnActualizar.setOnClickListener {
            val id = etId.text.toString().toIntOrNull() ?: 0
            val nombre = etNombre.text.toString()
            val precio = etPrecio.text.toString().toDoubleOrNull() ?: 0.0

            RetrofitClient.instance.actualizarProducto(id, nombre, precio).enqueue(object : Callback<RespuestaServidor> {
                override fun onResponse(call: Call<RespuestaServidor>, response: Response<RespuestaServidor>) {
                    val res = response.body()
                    if (response.isSuccessful && res?.estado == "success") {
                        Toast.makeText(this@ActualizarActivity, res.mensaje, Toast.LENGTH_SHORT).show()
                        irAlMenuPrincipal()
                    } else {
                        val errorMsg = res?.mensaje ?: "Error al actualizar"
                        Toast.makeText(this@ActualizarActivity, errorMsg, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<RespuestaServidor>, t: Throwable) {
                    Toast.makeText(this@ActualizarActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // --- 5. ELIMINAR ---
        btnEliminar.setOnClickListener {
            val id = etId.text.toString().toIntOrNull() ?: 0
            if (id == 0) return@setOnClickListener

            AlertDialog.Builder(this)
                .setTitle("Eliminar")
                .setMessage("¿Seguro?")
                .setPositiveButton("Sí, eliminar") { _, _ ->
                    RetrofitClient.instance.eliminarProducto(id).enqueue(object : Callback<RespuestaServidor> {
                        override fun onResponse(call: Call<RespuestaServidor>, response: Response<RespuestaServidor>) {
                            val res = response.body()
                            if (response.isSuccessful && res?.estado == "success") {
                                Toast.makeText(this@ActualizarActivity, "Eliminado", Toast.LENGTH_SHORT).show()
                                irAlMenuPrincipal()
                            }
                        }
                        override fun onFailure(call: Call<RespuestaServidor>, t: Throwable) {}
                    })
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun irAlMenuPrincipal() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun limpiarCampos(vararg editTexts: EditText) {
        for (et in editTexts) et.text.clear()
    }
}