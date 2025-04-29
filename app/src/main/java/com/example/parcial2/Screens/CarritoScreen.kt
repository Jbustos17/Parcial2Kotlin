package com.example.parcial2.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.parcial2.Data.Producto
import com.example.parcial2.Viewmodel.ProductoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(navController: NavController, viewModel: ProductoViewModel) {
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Carrito de Compras") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
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
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Finalizar Compra")
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f)
                ) {
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
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                model = producto.imagenUrl,
                contentDescription = producto.nombre,
                placeholder = painterResource(id = android.R.drawable.progress_indeterminate_horizontal),
                error = painterResource(id = android.R.drawable.ic_menu_report_image),
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(producto.nombre, fontWeight = FontWeight.Bold)
                Text("Precio: $${producto.precio}")
            }
        }
    }
}
