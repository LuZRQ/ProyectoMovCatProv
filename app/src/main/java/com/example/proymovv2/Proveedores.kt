package com.example.proymovv2

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proymovv2.db.AdminSQLiteOpenHelper
import com.example.proymovv2.model.Proveedor

class Proveedores : AppCompatActivity() {
    lateinit var btnGuardarProveedor: Button
    lateinit var txtId: EditText
    lateinit var txtNombre: EditText
    lateinit var txtNit: EditText
    lateinit var txtDireccion: EditText
    lateinit var txtTelefono: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_proveedores)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cargarR()
        estadoBoton()
    }

    private fun cargarR() {
        btnGuardarProveedor = findViewById(R.id.btnGuardarProveedor)
        txtId = findViewById(R.id.idProveedor)
        txtNombre = findViewById(R.id.nomProvedor)
        txtNit = findViewById(R.id.nitProveedor)
        txtDireccion = findViewById(R.id.adressProveedor)
        txtTelefono = findViewById(R.id.telefProvedor)
    }

    private fun estadoBoton() {
        btnGuardarProveedor.setOnClickListener {
            try {
                val adminsql = AdminSQLiteOpenHelper(this, "administracion", null, 1)
                val db = adminsql.writableDatabase
                val registro = ContentValues()


                val proveedor = Proveedor(
                    txtId.text.toString().toInt(),
                    txtNombre.text.toString(),
                    txtNit.text.toString(),
                    txtDireccion.text.toString(),
                    txtTelefono.text.toString().toInt()
                )
                registro.put("id_proveedor", proveedor.getId())
                registro.put("nombre", proveedor.getNombre())
                registro.put("nit", proveedor.getNit())
                registro.put("direccion", proveedor.getDireccion())
                registro.put("telefono", proveedor.getTelefono())

                db.insert("proveedores", null, registro)
                db.close()


                txtId.setText("")
                txtNombre.setText("")
                txtNit.setText("")
                txtDireccion.setText("")
                txtTelefono.setText("")

                Toast.makeText(this, "Proveedor guardado con éxito", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al guardar el proveedor: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}