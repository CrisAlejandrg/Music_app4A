package com.example.music_app4a

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION,

) {
    companion object{
        private const val DATABASE_NAME = "musica.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USUARIOS = "usuarios"
        private const val COL_ID      = "ID"
        private const val COL_NOMBRE = "nom"
        private const val COL_CORREO = "correo"
        private const val COL_PASSWORD = "password"

    }
    //METODO PARA CREAR LA BASE DE DATOS
    override fun onCreate(db: SQLiteDatabase?) {
        val crearTablaUsuarios = """
            CREATE TABLE $TABLE_USUARIOS (
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_NOMBRE TEXT NOT NULL,
            $COL_CORREO TEXT NOT NULL UNIQUE,
            $COL_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()

        //CREAR TABLA DE SQL
        db?.execSQL(crearTablaUsuarios)

    }

    //METODO PARA ACTUALIZAR LA BASE DE DATOS
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        onCreate(db)
    }

    //METODO PARA INSERTAR REGISTRO
    fun registrarUsuario(nombre : String, correo : String, password : String) : Boolean{

        //crear variables para que la base de datos se pueda modificar
        val db = writableDatabase

        //crear variable para recibir los datos que escribe el usuario
        val datos = ContentValues().apply {
            put(COL_NOMBRE, nombre)
            put(COL_CORREO, correo)
            put(COL_PASSWORD, password)
        }
        //INSERTAR DATOS EN LA TABLA
        val resultado = db.insert(TABLE_USUARIOS, null, datos)

        //cerrar la conexion con la bd
        db.close()

        //comparar el valor del resultado de la inserccion y regresarlo como boolean
        return resultado != -1L
    }

    //METODO PARA CONSULTAR A UN USUARIO
    fun iniciarSesion(correo : String, password : String): Boolean {

        val db = readableDatabase

        val query_sql = """
            SELECT * FROM $TABLE_USUARIOS
            WHERE $COL_CORREO = ? AND $COL_PASSWORD = ?
        """.trimIndent()

     //crear cursor para recorrer la informaciom
        val cursor = db.rawQuery(
            query_sql, arrayOf(correo, password)
        )

        //crear variable de tipo bool que permita saber si el cursor encontro un registro
        val usuarioEncontrado = cursor.count > 0

        cursor.close()
        db.close()

        return usuarioEncontrado
    }

}