package com.example.proymovv2.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.Cursor
import com.example.proymovv2.model.Productos

class AdminSQLiteOpenHelper (
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    companion object {
        // Constantes para la tabla de productos
        const val TABLE_PRODUCTOS = "productos"
        const val COLUMN_ID_PRODUCTOS = "id_productos"
        const val COLUMN_NOMBRE_PRODUCTO = "nombre"
        const val COLUMN_PRECIO = "precio"
        const val COLUMN_PRODUCTO_ID_CATEGORIA = "id_categoria"
        const val COLUMN_PRODUCTO_ID_PROVEEDOR = "id_proveedor"

        // Constantes para la tabla de proveedores
        const val TABLE_PROVEEDORES = "proveedores"
        const val COLUMN_ID_PROVEEDOR = "id_proveedor"
        const val COLUMN_NOMBRE_PROVEEDOR = "nombre"
        const val COLUMN_NIT = "nit"
        const val COLUMN_DIRECCION = "direccion"
        const val COLUMN_TELEFONO = "telefono"

        // Constantes para la tabla de categorías
        const val TABLE_CATEGORIAS = "categorias"
        const val COLUMN_ID_CATEGORIA = "id_categoria"
        const val COLUMN_NOMBRE_CATEGORIA = "nombre"
        const val COLUMN_DESCRIPCION = "descripcion"
    }

    override fun onCreate(db: SQLiteDatabase?) {
            // Activar claves foráneas
            db?.execSQL("PRAGMA foreign_keys=ON;")

            // Creación de las tablas
            db?.execSQL(
                "CREATE TABLE $TABLE_PRODUCTOS (" +
                        "$COLUMN_ID_PRODUCTOS INTEGER PRIMARY KEY, " +
                        "$COLUMN_NOMBRE_PRODUCTO TEXT, " +
                        "$COLUMN_PRECIO REAL, " +
                        "$COLUMN_PRODUCTO_ID_CATEGORIA INTEGER, " +
                        "$COLUMN_PRODUCTO_ID_PROVEEDOR INTEGER, " +
                        "FOREIGN KEY ($COLUMN_PRODUCTO_ID_PROVEEDOR) REFERENCES $TABLE_PROVEEDORES($COLUMN_ID_PROVEEDOR), " +
                        "FOREIGN KEY ($COLUMN_PRODUCTO_ID_CATEGORIA) REFERENCES $TABLE_CATEGORIAS($COLUMN_ID_CATEGORIA))"
            )
            db?.execSQL(
                "CREATE TABLE $TABLE_PROVEEDORES (" +
                        "$COLUMN_ID_PROVEEDOR INTEGER PRIMARY KEY, " +
                        "$COLUMN_NOMBRE_PROVEEDOR TEXT, " +
                        "$COLUMN_NIT TEXT, " +
                        "$COLUMN_DIRECCION TEXT, " +
                        "$COLUMN_TELEFONO TEXT)" // Cambié a TEXT por si hay códigos de área
            )

            db?.execSQL(
                "CREATE TABLE $TABLE_CATEGORIAS (" +
                        "$COLUMN_ID_CATEGORIA INTEGER PRIMARY KEY, " +
                        "$COLUMN_NOMBRE_CATEGORIA TEXT, " +
                        "$COLUMN_DESCRIPCION TEXT)"
            )
        }



    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTOS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PROVEEDORES")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORIAS")
        onCreate(db)
    }

    // Método para agregar un producto
    fun agregarProducto(producto: Productos): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_ID_PRODUCTOS, producto.getProductos()) // Usar el ID proporcionado
            put(COLUMN_NOMBRE_PRODUCTO, producto.getNombre())
            put(COLUMN_PRECIO, producto.getPrecio())
            put(COLUMN_PRODUCTO_ID_CATEGORIA, producto.getIdCategoria())
            put(COLUMN_PRODUCTO_ID_PROVEEDOR, producto.getIdProveedor())
        }
        val result = db.insert(TABLE_PRODUCTOS, null, contentValues)
        db.close()
        return result
    }


    fun obtenerProductoPorId(id: Int): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_PRODUCTOS WHERE $COLUMN_ID_PRODUCTOS = ?", arrayOf(id.toString()))
    }


    // Método para actualizar un producto
    fun actualizarProducto(producto: Productos): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NOMBRE_PRODUCTO, producto.getNombre())
            put(COLUMN_PRECIO, producto.getPrecio())
            put(COLUMN_PRODUCTO_ID_CATEGORIA, producto.getIdCategoria())
            put(COLUMN_PRODUCTO_ID_PROVEEDOR, producto.getIdProveedor())
        }
        val result = db.update(
            TABLE_PRODUCTOS,
            contentValues,
            "$COLUMN_ID_PRODUCTOS = ?",
            arrayOf(producto.getProductos().toString()) // Usar el ID proporcionado
        )
        db.close()
        return result
    }


    // Método para eliminar un producto
    fun eliminarProducto(id: Int): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_PRODUCTOS, "$COLUMN_ID_PRODUCTOS = ?", arrayOf(id.toString()))
        db.close()
        return result
    }



    // Método para obtener una categoría por ID
    fun obtenerCategoriaPorId(id: Int): Cursor? {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_CATEGORIAS WHERE $COLUMN_ID_CATEGORIA = ?", arrayOf(id.toString()))
    }

    // Método para obtener un proveedor por ID
    fun obtenerProveedorPorId(id: Int): Cursor? {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_PROVEEDORES WHERE $COLUMN_ID_PROVEEDOR = ?", arrayOf(id.toString()))
    }
}