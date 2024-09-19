package com.example.proymovv2.model

class Proveedor constructor(id:Int,nombre: String,nit: String,direccion: String,telefono: Int){
    private var id: Int=id
    private var nombre: String=nombre
    private var nit: String=nit
    private var direccion: String=direccion
    private var telefono: Int=telefono


    // Getters
    fun getId(): Int {
        return id
    }

    fun getNombre(): String {
        return nombre
    }

    fun getNit(): String {
        return nit
    }

    fun getDireccion(): String {
        return direccion
    }

    fun getTelefono(): Int {
        return telefono
    }

    // Setters
    fun setId(id: Int) {
        this.id = id
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun setNit(nit: String) {
        this.nit = nit
    }

    fun setDireccion(direccion: String) {
        this.direccion = direccion
    }

    fun setTelefono(telefono: Int) {
        this.telefono = telefono
    }
}