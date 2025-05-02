package com.example.parcial2.Screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.parcial2.R
import com.example.parcial2.Viewmodel.ProductoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(navController: NavController, viewModel: ProductoViewModel, productoId: Int?) {
    val producto = productoId?.let { viewModel.obtenerProductoPorId(it) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalle del Producto") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        if (producto == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Producto no encontrado")
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val painter = rememberAsyncImagePainter(
                    model = producto.imagenUrl,
                    onState = { state ->
                        if (state is AsyncImagePainter.State.Error) {
                            // imagen con error
                        }
                    }
                )
                AsyncImage(
                    model = producto.imagenUrl,
                    contentDescription = producto.nombre,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(producto.nombre, style = MaterialTheme.typography.titleLarge)
                Text("Precio: $${producto.precio}", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(producto.descripcion, style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(24.dp))

                Row {
                    Button(
                        onClick = {
                            viewModel.agregarAlCarrito(producto)
                            scope.launch {
                                snackbarHostState.showSnackbar("Producto agregado al carrito")
                            }
                            navController.popBackStack()
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004A93))
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Agregar")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Agregar al Carrito")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    OutlinedButton(
                        onClick = { navController.popBackStack() },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Volver")
                    }
                }
            }
        }
    }
}
