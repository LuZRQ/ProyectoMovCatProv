package com.example.proymovv2

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proymovv2.db.AdminSQLiteOpenHelper
import com.example.proymovv2.model.Productos
class CalcularIva : AppCompatActivity() {
    lateinit var btnCalcular: Button
    lateinit var btnBuscar: Button
    lateinit var txtPrecio: EditText
    lateinit var txtNombre:EditText
    lateinit var tvResul: TextView
    lateinit var spLista:Spinner
    lateinit var listPro:ListView

    lateinit var productosList:MutableList<String>
    lateinit var adapterListView:ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calcular_iva)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //codigo
        cargarR()
        estadoOnclick()
        cargarListaProducto()

        val listaPaises = arrayOf("USA", "BOL", "ESP")
        val adaptador1 = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaPaises)
        spLista.adapter = adaptador1


    }
    fun cargarR (){
        btnCalcular = findViewById(R.id.btnCalcular)
        txtPrecio = findViewById(R.id.txtPrecio)
        tvResul = findViewById(R.id.tvResultado)
        spLista = findViewById(R.id.spPaises)
        listPro = findViewById(R.id.listaProductos)
        txtNombre = findViewById(R.id.txtNombre)
        btnBuscar=findViewById(R.id.btnBuscar)
    }

    fun estadoOnclick(){
        btnCalcular.setOnClickListener(){

            val laptop = Productos(txtNombre.text.toString(), txtPrecio.text.toString().toDouble(), 0,0,0)

            //val datosRe : Double = laptop.calIVA()

            when(spLista.selectedItem.toString()){
                "USA"-> productosList.add(laptop.getNombre() + ", " +laptop.getPrecio()+ "IVA: "+laptop.calIVA(0.03).toString())
                "BOL"-> productosList.add(laptop.getNombre()+ ", " +laptop.getPrecio()+ "IVA: "+laptop.calIVA(0.13).toString())
                "ESP"-> productosList.add(laptop.getNombre() + ", " +laptop.getPrecio()+ "IVA: "+laptop.calIVA(0.05).toString())
            }
            listPro.adapter=adapterListView

        }

        //al biton buscar
        btnBuscar.setOnClickListener{
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("select nombre,precio from productos where id_productos=${txtNombre.text.toString()}", null)
            if (fila.moveToFirst()) {
                txtNombre.setText(fila.getString(0))
                txtPrecio.setText(fila.getString(1))
            } else
                Toast.makeText(this, "No existe un producto con dicho código",  Toast.LENGTH_SHORT).show()
            bd.close()
        }
    }



    fun cargarListaProducto(){
        //val productos = arrayOf("LAPTOP", "MOUSE")
        productosList = mutableListOf("3500")
        adapterListView = ArrayAdapter(this, android.R.layout.simple_list_item_1,productosList)
        listPro.adapter=adapterListView
    }

}

