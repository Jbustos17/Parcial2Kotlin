package com.example.parcial2.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.parcial2.Viewmodel.ProductoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(navController: NavController, viewModel: ProductoViewModel, productoId: Int?) {
    val producto = productoId?.let { viewModel.obtenerProductoPorId(it) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalle del Producto") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        }
    ) { paddingValues ->
        if (producto == null) {
            Box(modifier = Modifier
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
                            // Imagen con error
                        }
                    }
                )

                AsyncImage(
                    model = producto.imagenUrl,
                    contentDescription = producto.nombre,
                    placeholder = painterResource(id = android.R.drawable.progress_indeterminate_horizontal),
                    error = painterResource(id = android.R.drawable.ic_menu_report_image),
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(producto.nombre, style = MaterialTheme.typography.titleLarge)
                Text("Precio: $${producto.precio}", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(producto.descripcion, style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(24.dp))

                Row {
                    Button(onClick = {
                        viewModel.agregarAlCarrito(producto)
                        navController.popBackStack()
                    }) {
                        Text("Agregar al Carrito")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedButton(onClick = { navController.popBackStack() }) {
                        Text("Volver")
                    }
                }
            }
        }
    }
}
