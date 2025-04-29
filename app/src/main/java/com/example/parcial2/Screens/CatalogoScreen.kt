package com.example.parcial2.Screens

import androidx.compose.foundation.Image
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.parcial2.Data.Producto
import com.example.parcial2.Screens.Screens
import com.example.parcial2.Viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class) //se utiliza para indicar explícitamente que estás usando una API experimental
@Composable
fun CatalogoScreen(navController: NavController, viewModel: ProductoViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Catálogo de Productos") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
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
                Button(onClick = { navController.navigate(Screens.Registro.route) }) {
                    Text("Agregar")
                }
                Button(onClick = { navController.navigate(Screens.Carrito.route) }) {
                    Text("Carrito")
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(viewModel.productos) { producto ->
                ProductoItem(producto = producto) {
                    navController.navigate(Screens.Detalle.createRoute(producto.id))
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
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = producto.imagenUrl,
                    placeholder = painterResource(id = android.R.drawable.progress_indeterminate_horizontal),
                    error = painterResource(id = android.R.drawable.ic_menu_report_image)
                ),
                contentDescription = producto.nombre,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(producto.nombre, style = MaterialTheme.typography.titleMedium)
                Text("Precio: $${producto.precio}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
