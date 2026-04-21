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
    }
}