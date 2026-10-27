package com.example.music_app4a

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etCorreo: EditText
    private lateinit var etPass: EditText
    private lateinit var btnInicio : Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val tvCrearCuenta = findViewById<TextView>(R.id.tvCrearCuenta)

        etCorreo = findViewById(R.id.etCorreo)
        etPass = findViewById(R.id.etPassword)
        btnInicio = findViewById(R.id.btnIngresar)

        dbHelper = DatabaseHelper(this)

        //EVENTO PARA INICIAR SESION
        btnInicio.setOnClickListener {
            iniciarSesion()
        }

        tvCrearCuenta.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    //METODO PARA INICIAR SESION
    private fun iniciarSesion(){
        //acceder a la informacion que escribe el usuario
        val correo = etCorreo.text.toString().trim()
        val pass = etPass.text.toString().trim()

        if (correo.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        //llamar el metodo de inicio de sesion en la BD
        val resultado = dbHelper.iniciarSesion(correo, pass)

        if (resultado){
            //si las sesion es correcta
            Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, HomeActivity:: class.java)
            startActivity(intent)
            //cerrar la pantalla actual
            finish()
        } else {
            //si la sesion es incorrecta
            Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show()
        }
    }
}