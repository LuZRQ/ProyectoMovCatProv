package com.example.proymovv2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Home : AppCompatActivity() {
    lateinit var btnPro: CardView
    lateinit var btnProved: CardView
    lateinit var btnExit: CardView
    lateinit var btnCategorie: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cargarR()

        estadoBoton()
    }

    fun cargarR(){
        btnPro = findViewById(R.id.btnProducto)
        btnProved = findViewById(R.id.btnProveedor)
        btnCategorie=findViewById(R.id.btnCategoria)
        btnExit=findViewById(R.id.btnSalir)


    }

    fun estadoBoton(){
        btnPro.setOnClickListener{
            val i = Intent(this, RegisProduct::class.java)
            startActivity(i)
        }

        btnProved.setOnClickListener{
            val i = Intent(this, Proveedores::class.java)
            startActivity(i)
        }
        btnCategorie.setOnClickListener{
            val i = Intent(this, Categoria::class.java)
            startActivity(i)
        }
        btnExit.setOnClickListener{
            val i = Intent(this, CalcularIva::class.java)
            startActivity(i)
        }

    }
}