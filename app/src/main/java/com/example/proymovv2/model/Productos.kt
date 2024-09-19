package com.example.proymovv2.model

class Productos constructor(nombre:String, precio:Double , productos:Int, idCategoria: Int ,idProveedor: Int ){

    private var nombre:String = nombre
    private var precio:Double = precio
    private  var id_productos:Int=productos
    private var id_categoria: Int = idCategoria
    private var id_proveedor: Int = idProveedor

    //get
    fun getNombre():String{
        return nombre
    }
    fun getPrecio():Double{
        return precio
    }
    fun getProductos():Int{
        return id_productos
    }
    fun getIdCategoria(): Int {
        return id_categoria
    }

    fun getIdProveedor(): Int {
        return id_proveedor
    }
    //set
    fun setNombre(nombre:String){
        this.nombre= nombre

    }
    fun setPrecio(precio:Double){
        this.precio= precio
    }
    fun setProductos(productos: Int){
        this.id_productos= productos
    }
    fun setIdCategoria(categoria: Int) {
        this.id_categoria = categoria
    }

    fun setIdProveedor(proveedor: Int) {
        this.id_proveedor = proveedor
    }
    fun calIVA(iva:Double): Double{
        val total:Double =precio * iva
        return total
    }
}