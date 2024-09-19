package com.example.proymovv2

import android.os.Bundle

import android.content.ContentValues
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proymovv2.db.AdminSQLiteOpenHelper
import com.example.proymovv2.model.Categorie

class Categoria : AppCompatActivity() {
    lateinit var btnGuardarCateg: Button
    lateinit var txtIdCategoria: EditText
    lateinit var txtNomCategoria: EditText
    lateinit var txtDescCategoria: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_categoria)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cargarA()
        estadoBoton()
    }

    private fun cargarA() {
        btnGuardarCateg = findViewById(R.id.btnGuardarCateg)
        txtIdCategoria = findViewById(R.id.idCategoria)
        txtNomCategoria = findViewById(R.id.nomCategoria)
        txtDescCategoria = findViewById(R.id.descripCategoria)
    }

    private fun estadoBoton() {
        btnGuardarCateg.setOnClickListener {
            try {
                val adminsql = AdminSQLiteOpenHelper(this, "administracion", null, 1)
                val db = adminsql.writableDatabase
                val registro = ContentValues()


                val categoria = Categorie(
                    txtIdCategoria.text.toString().toInt(),
                    txtNomCategoria.text.toString(),
                    txtDescCategoria.text.toString()
                )

                registro.put("id_categoria", categoria.getIdCategoria())
                registro.put("nombre", categoria.getNombre())
                registro.put("descripcion", categoria.getDescripcion())

                db.insert("categorias", null, registro)
                db.close()

                txtIdCategoria.setText("")
                txtNomCategoria.setText("")
                txtDescCategoria.setText("")

                Toast.makeText(this, "Categoría registrada exitosamente", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al registrar la categoría: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}