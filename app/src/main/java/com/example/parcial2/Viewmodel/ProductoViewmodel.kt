package com.example.parcial2.Viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.parcial2.Data.Producto

class ProductoViewModel : ViewModel() {


    private val _productos = mutableStateListOf<Producto>()
    val productos: List<Producto> get() = _productos


    private val _carrito = mutableStateListOf<Producto>()
    val carrito: List<Producto> get() = _carrito


    fun agregarProducto(producto: Producto) {
        _productos.add(producto)
    }


    fun agregarAlCarrito(producto: Producto) {
        _carrito.add(producto)
    }


    fun obtenerProductoPorId(id: Int): Producto? {
        return _productos.find { it.id == id }
    }


    fun totalCarrito(): Double {
        return _carrito.sumOf { it.precio }
    }


    fun limpiarCarrito() {
        _carrito.clear()
    }
}
