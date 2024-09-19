package com.example.proymovv2.model

class Categorie constructor(  categoria:Int, nombre: String,descripcion: String) {
    private var idCategoria: Int=categoria
    private var nombre: String=nombre
    private var descripcion: String=descripcion

    // Getters
    fun getIdCategoria(): Int {
        return idCategoria
    }

    fun getNombre(): String {
        return nombre
    }

    fun getDescripcion(): String {
        return descripcion
    }

    // Setters
    fun setIdCategoria(idCategoria: Int) {
        this.idCategoria = idCategoria
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun setDescripcion(descripcion: String) {
        this.descripcion = descripcion
    }
}