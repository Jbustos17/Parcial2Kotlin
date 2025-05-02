package com.example.parcial2.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.parcial2.Data.Producto
import com.example.parcial2.R
import com.example.parcial2.Viewmodel.ProductoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroProductoScreen(navController: NavController, viewModel: ProductoViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }

    // Estados de error
    var nombreError by remember { mutableStateOf(false) }
    var precioError by remember { mutableStateOf(false) }
    var descripcionError by remember { mutableStateOf(false) }
    var imagenError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.agregarproductos),
                            contentDescription = "Logo",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Agregar Producto")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    nombreError = false
                },
                label = { Text("Nombre") },
                isError = nombreError,
                modifier = Modifier.fillMaxWidth()
            )
            if (nombreError) Text("Campo requerido", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = precio,
                onValueChange = {
                    precio = it
                    precioError = false
                },
                label = { Text("Precio") },
                isError = precioError,
                modifier = Modifier.fillMaxWidth()
            )
            if (precioError) Text("Campo requerido o inválido", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = {
                    descripcion = it
                    descripcionError = false
                },
                label = { Text("Descripción") },
                isError = descripcionError,
                modifier = Modifier.fillMaxWidth()
            )
            if (descripcionError) Text("Campo requerido", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = imagenUrl,
                onValueChange = {
                    imagenUrl = it
                    imagenError = false
                },
                label = { Text("URL de la imagen") },
                isError = imagenError,
                modifier = Modifier.fillMaxWidth()
            )
            if (imagenError) Text("Campo requerido", color = Color.Red, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Button(
                    onClick = {
                        // Resetear errores
                        nombreError = nombre.isBlank()
                        precioError = precio.isBlank() || precio.toDoubleOrNull() == null
                        descripcionError = descripcion.isBlank()
                        imagenError = imagenUrl.isBlank()

                        if (nombreError || precioError || descripcionError || imagenError) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Por favor, corrige los errores")
                            }
                        } else {
                            viewModel.agregarProducto(
                                Producto(
                                    id = viewModel.productos.size + 1,
                                    nombre = nombre,
                                    precio = precio.toDouble(),
                                    descripcion = descripcion,
                                    imagenUrl = imagenUrl.trim().replace(" ", "%20")
                                )
                            )
                            scope.launch {
                                snackbarHostState.showSnackbar("Producto guardado correctamente")
                            }
                            navController.navigate(Screens.Catalogo.route)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004A93))
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Guardar")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Guardar")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004A93))
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}
