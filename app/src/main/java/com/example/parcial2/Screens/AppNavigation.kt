package com.example.parcial2.Screens


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.parcial2.Screens.*;
import com.example.parcial2.Viewmodel.ProductoViewModel;

sealed class Screens(val route: String) {
    object Catalogo : Screens("catalogo")
    object Registro : Screens("registro")
    object Detalle : Screens("detalle/{productoId}") {
        fun createRoute(productoId: Int) = "detalle/$productoId"
    }
    object Carrito : Screens("carrito")
}

@Composable
fun AppNavigation(navController: NavHostController, viewModel: ProductoViewModel) {
    NavHost(navController = navController, startDestination = Screens.Catalogo.route) {
        composable(Screens.Catalogo.route) {
            CatalogoScreen(navController, viewModel)
        }
        composable(Screens.Registro.route) {
            RegistroProductoScreen(navController, viewModel)
        }
        composable(Screens.Detalle.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("productoId")?.toIntOrNull()
            DetalleScreen(navController, viewModel, id)
        }
        composable(Screens.Carrito.route) {
            CarritoScreen(navController, viewModel)
        }
    }
}
