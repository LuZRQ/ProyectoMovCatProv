package com.example.proymovv2

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proymovv2.db.AdminSQLiteOpenHelper
import com.example.proymovv2.model.Productos


class RegisProduct : AppCompatActivity() {
    lateinit var btnRegistroPro: Button
    lateinit var txtNom: EditText
    lateinit var txtPrecio: EditText
    lateinit var txtCodigo: EditText

    lateinit var btnBuscarrProv: Button
    lateinit var txtIdProvv: EditText
    lateinit var txtNombreP: EditText
    lateinit var txtNitP: EditText
    lateinit var txtDireccion: EditText
    lateinit var txtTelefono: EditText

    lateinit var btnBuscarCate: Button
    lateinit var txtIdCategoria: EditText
    lateinit var txtNomCategoria: EditText
    lateinit var txtDescCategoria: EditText

    private lateinit var listVProduc: ListView

    private lateinit var adapter: ArrayAdapter<String>
    private val productosList = ArrayList<String>()

    private lateinit var adminsql: AdminSQLiteOpenHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_regis_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        adminsql = AdminSQLiteOpenHelper(this, "administracion", null, 1)

        cargarR()
        estadoBoton()
        buscarCategoria()
        buscarProveedor()


    }

    private fun cargarR() {
        btnRegistroPro = findViewById(R.id.btnRegistroProducto)
        txtNom = findViewById(R.id.txtNombre)
        txtPrecio = findViewById(R.id.txtPrecio)
        txtCodigo = findViewById(R.id.txtCodigoProducto)

        btnBuscarrProv = findViewById(R.id.btnBuscarProveedor)
        txtIdProvv = findViewById(R.id.idProveedor)
        txtNombreP = findViewById(R.id.nomProveedor)
        txtNitP = findViewById(R.id.nitProveedores)
        txtDireccion = findViewById(R.id.direccionProveedor)
        txtTelefono = findViewById(R.id.telProveedor)

        btnBuscarCate = findViewById(R.id.btnBuscarCategoria)
        txtIdCategoria = findViewById(R.id.idCategoria)
        txtNomCategoria = findViewById(R.id.nomCategoria)
        txtDescCategoria = findViewById(R.id.descripCategoria)

        listVProduc = findViewById(R.id.listDatosProductos)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, productosList)
        listVProduc.adapter = adapter
    }

    private fun estadoBoton() {
        btnRegistroPro.setOnClickListener {
            // Verificamos que los campos no estén vacíos
            if (txtCodigo.text.isEmpty() || txtNom.text.isEmpty() || txtPrecio.text.isEmpty() || txtIdCategoria.text.isEmpty() || txtIdProvv.text.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val adminsql = AdminSQLiteOpenHelper(this, "administracion", null, 1)
                val db = adminsql.writableDatabase
                val registro = ContentValues()

                // Creamos el objeto Producto
                val objeto = Productos(
                    txtNom.text.toString(), // Nombre del producto
                    txtPrecio.text.toString().toDouble(), // Precio del producto
                    txtCodigo.text.toString().toInt(), // Código del producto
                    txtIdProvv.text.toString().toInt(), // ID del proveedor
                    txtIdCategoria.text.toString().toInt() // ID de la categoría
                )

                // Registro de depuración para verificar los valores del objeto
                Log.d("DEBUG", "Producto creado: ${objeto.getProductos()}, ${objeto.getNombre()}, ${objeto.getPrecio()}, ${objeto.getIdCategoria()}, ${objeto.getIdProveedor()}")

                // Insertamos en la base de datos
                registro.put("id_productos", objeto.getProductos())
                registro.put("nombre", objeto.getNombre())
                registro.put("precio", objeto.getPrecio())
                registro.put("id_proveedor", objeto.getIdProveedor())
                registro.put("id_categoria", objeto.getIdCategoria())

                val result = db.insert("productos", null, registro)
                db.close()

                // Verifica si la inserción fue exitosa
                if (result == -1L) {
                    Toast.makeText(this, "Error al registrar el producto", Toast.LENGTH_SHORT).show()
                } else {
                    // Limpiamos los campos de texto
                    limpiarCampos()

                    // Añadimos el nuevo producto a la lista con un formato bonito
                    productosList.add(
                        "ID: ${objeto.getProductos()}  \n" +
                                "Nombre: ${objeto.getNombre()} \n" +
                                "Precio: $${objeto.getPrecio()}  \n" +
                                "Categoría: ${objeto.getIdCategoria()} \n" +
                                "Proveedor: ${objeto.getIdProveedor()} \n" +
                                "----------------------------"
                    )

                    // Notificamos al adaptador que los datos han cambiado
                    adapter.notifyDataSetChanged()

                    Toast.makeText(this, "Producto registrado", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                // Manejamos cualquier excepción que ocurra durante la ejecución
                Toast.makeText(this, "Error al registrar el producto: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }




    private fun limpiarCampos() {
        txtCodigo.text.clear()
        txtNom.text.clear()
        txtPrecio.text.clear()
        txtIdCategoria.text.clear()
        txtNomCategoria.text.clear()
        txtDescCategoria.text.clear()
        txtIdProvv.text.clear()
        txtNombreP.text.clear()
        txtTelefono.text.clear()
        txtNitP.text.clear()
        txtDireccion.text.clear()
    }

    private fun buscarCategoria() {
        btnBuscarCate.setOnClickListener {
            val idCategoria = txtIdCategoria.text.toString().toIntOrNull()
            if (idCategoria != null) {
                try {
                    val cursor = adminsql.obtenerCategoriaPorId(idCategoria)
                    if (cursor != null && cursor.moveToFirst()) {
                        txtNomCategoria.setText(cursor.getString(cursor.getColumnIndexOrThrow("nombre")))
                        txtDescCategoria.setText(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")))
                        cursor.close()
                    } else {
                        Toast.makeText(this, "Categoría no encontrada", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error: " + e.message, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun buscarProveedor() {
        btnBuscarrProv.setOnClickListener {
            val idProveedor = txtIdProvv.text.toString().toIntOrNull()
            if (idProveedor != null) {
                try {
                    val cursor = adminsql.obtenerProveedorPorId(idProveedor)
                    if (cursor != null && cursor.moveToFirst()) {
                        txtNombreP.setText(cursor.getString(cursor.getColumnIndexOrThrow("nombre")))
                        txtNitP.setText(cursor.getString(cursor.getColumnIndexOrThrow("nit")))
                        txtDireccion.setText(cursor.getString(cursor.getColumnIndexOrThrow("direccion")))
                        txtTelefono.setText(cursor.getString(cursor.getColumnIndexOrThrow("telefono")))
                        cursor.close()
                    } else {
                        Toast.makeText(this, "Proveedor no encontrado", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error: " + e.message, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show()
            }
        }
    }




}

