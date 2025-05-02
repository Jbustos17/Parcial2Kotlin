package com.example.parcial2.Screens

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
fun CarritoScreen(navController: NavController, viewModel: ProductoViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.carrito),
                            contentDescription = "Logo",
                            modifier = Modifier.size(300.dp)
                        )

                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(viewModel.carrito) { producto ->
                    CarritoItem(producto)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Total a pagar: $${viewModel.totalCarrito()}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Button(
                    onClick = {
                        mostrarConfirmacion = true
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004A93))
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Finalizar Compra")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Finalizar Compra")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004A93))
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Volver")
                }
            }
        }

        if (mostrarConfirmacion) {
            AlertDialog(
                onDismissRequest = { mostrarConfirmacion = false },
                title = { Text("Compra Realizada") },
                text = { Text("¡Gracias por su compra!") },
                confirmButton = {
                    TextButton(onClick = {
                        mostrarConfirmacion = false
                        viewModel.limpiarCarrito()
                        scope.launch {
                            snackbarHostState.showSnackbar("Carrito vaciado después de la compra")
                        }
                        navController.popBackStack()
                    }) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}

@Composable
fun CarritoItem(producto: Producto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFEFEF))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
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
                Text("Precio: $${producto.precio}", style = MaterialTheme.typography.titleMedium)
                Text(producto.descripcion, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
