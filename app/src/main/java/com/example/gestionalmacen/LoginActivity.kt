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

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val usuario = etUsuario.text.toString()
            val password = etPassword.text.toString()

            if (usuario.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor llena ambos campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // CORRECCIÓN: Usamos Callback<RespuestaServidor> para que coincida con el ApiService
            RetrofitClient.instance.login(usuario, password).enqueue(object : Callback<RespuestaServidor> {
                override fun onResponse(call: Call<RespuestaServidor>, response: Response<RespuestaServidor>) {
                    if (response.isSuccessful) {
                        val res = response.body()
                        // Verificamos si el PHP nos devolvió un estado de éxito
                        if (res?.estado == "success") {
                            Toast.makeText(this@LoginActivity, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Si el PHP dice que las credenciales no existen
                            Toast.makeText(this@LoginActivity, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<RespuestaServidor>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}