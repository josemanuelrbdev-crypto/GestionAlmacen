package com.example.gestionalmacen

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerProductosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_productos)

        val rvProductos = findViewById<RecyclerView>(R.id.rvProductos)
        rvProductos.layoutManager = LinearLayoutManager(this)

        // Llamamos a la función para cargar datos automáticamente
        cargarProductos(rvProductos)
    }

    private fun cargarProductos(recyclerView: RecyclerView) {
        RetrofitClient.instance.obtenerProductos().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    val adapter = ProductoAdapter(lista)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Toast.makeText(this@VerProductosActivity, "Error al conectar", Toast.LENGTH_SHORT).show()
            }
        })
    }
}