package com.example.music_app4a

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNombre : EditText
    private lateinit var etCorreo : EditText
    private lateinit var etPassword : EditText
    private lateinit var btnRegistrar : Button

    //crear objeto de la clase de la base de datos
    private lateinit var dbHelper : DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        etNombre = findViewById(R.id.etNombre)
        etCorreo = findViewById(R.id.etCorreo)
        etPassword = findViewById(R.id.etPassword)
        btnRegistrar = findViewById(R.id.btnResgistar)

        //iniciar el objeto de la base de datos
        dbHelper = DatabaseHelper(this)

        //evento para registrar a un usuario
        btnRegistrar.setOnClickListener {
            //llamar al metodo de incio de sesion
            registrarUsuario()
        }
    }

    //METODO PARA REGISTRAR A UN USUARIO
    private fun registrarUsuario(){
        //obtener la informacion que escribe al usario
        val nombre = etNombre.text.toString().trim()
        val correo = etCorreo.text.toString().trim()
        val password = etPassword.text.toString().trim()

        //validar que los campos tenga informacion
        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty() ) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        //LLAMAR A NUESTRA BASE DE DATOS Y GUARDAR EL RESULTADO DE LA INSERCION
        val resultado = dbHelper.registrarUsuario(nombre, correo, password)

        if (resultado){
            //si el resultado es verdadero
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
            // resgresar al  usuario a la pantalla anterior
            finish()
        } else{
            Toast.makeText(this, "Registro fallido", Toast.LENGTH_SHORT).show()
        }

    }

}