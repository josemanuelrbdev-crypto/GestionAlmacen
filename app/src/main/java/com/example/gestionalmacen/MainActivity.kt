package com.example.gestionalmacen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnIrCapturar).setOnClickListener {
            startActivity(Intent(this, CapturarActivity::class.java))
        }

        findViewById<Button>(R.id.btnIrVer).setOnClickListener {
            startActivity(Intent(this, VerProductosActivity::class.java))
        }

        findViewById<Button>(R.id.btnIrActualizar).setOnClickListener {
            startActivity(Intent(this, ActualizarActivity::class.java))
        }
        // --- LÓGICA DE DESLOGUEAR ---
        findViewById<Button>(R.id.btnCerrarSesion).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)

            // Estas banderas limpian todas las actividades anteriores
            // y hacen que el Login sea la única pantalla en la pila.
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish() // Cierra el MainActivity
        }
    }
}