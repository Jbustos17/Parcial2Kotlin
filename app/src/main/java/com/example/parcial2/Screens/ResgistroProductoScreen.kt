package com.example.parcial2.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.parcial2.Data.Producto
import com.example.parcial2.Viewmodel.ProductoViewModel


@Composable
fun RegistroProductoScreen(navController: NavController, viewModel: ProductoViewModel) {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Agregar Producto") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = imagenUrl,
                onValueChange = { imagenUrl = it },
                label = { Text("URL de la imagen") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Button(
                    onClick = {
                        if (nombre.isNotBlank() && precio.isNotBlank() && descripcion.isNotBlank() && imagenUrl.isNotBlank()) {
                            viewModel.agregarProducto(
                                Producto(
                                    id = viewModel.productos.size + 1,
                                    nombre = nombre,
                                    precio = precio.toDoubleOrNull() ?: 0.0,
                                    descripcion = descripcion,
                                    imagenUrl = imagenUrl
                                )
                            )
                            navController.navigate(Screens.Catalogo.route)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Guardar")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}
