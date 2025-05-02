package com.example.parcial2.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.parcial2.Data.Producto
import com.example.parcial2.R
import com.example.parcial2.Viewmodel.ProductoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(navController: NavController, viewModel: ProductoViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logoprincipal),
                            contentDescription = "Logo",
                            modifier = Modifier.size(260.dp)
                        )

                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Text(
                    text = "Total: $${viewModel.totalCarrito()}",
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f),
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = { navController.navigate(Screens.Registro.route) },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004A93))
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Agregar")
                }
                Button(
                    onClick = { navController.navigate(Screens.Carrito.route) },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004A93))
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Carrito")
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(viewModel.productos) { producto ->
                ProductoItem(producto = producto) {
                    navController.navigate(Screens.Detalle.createRoute(producto.id))
                    scope.launch {
                        snackbarHostState.showSnackbar("Vista de detalle abierta")
                    }
                }
            }
        }
    }
}

@Composable
fun ProductoItem(producto: Producto, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFEFEF))
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = producto.imagenUrl.trim(),
                contentDescription = producto.nombre,
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.error),
                modifier = Modifier
                    .size(120.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(producto.nombre, style = MaterialTheme.typography.titleLarge)
                Text(producto.descripcion, style = MaterialTheme.typography.bodyLarge)
                Text("Precio: $${producto.precio}", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
