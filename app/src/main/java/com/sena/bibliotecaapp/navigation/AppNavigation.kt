package com.sena.bibliotecaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sena.bibliotecaapp.ui.screens.inicio.InicioScreen
import com.sena.bibliotecaapp.ui.screens.libros.ConfirmacionScreen
import com.sena.bibliotecaapp.ui.screens.libros.DetalleLibroScreen
import com.sena.bibliotecaapp.ui.screens.libros.ListaLibrosScreen
import com.sena.bibliotecaapp.ui.screens.reservas.DetalleReservaScreen
import com.sena.bibliotecaapp.ui.screens.reservas.MisReservasScreen
import com.sena.bibliotecaapp.ui.screens.reservas.RenovacionExitosaScreen
import com.sena.bibliotecaapp.ui.screens.reservas.RenovarReservaScreen

/**
 * Gestor Central de Navegación de la Aplicación.
 *
 * CONCEPTOS CLAVE PARA EVALUACIÓN:
 * 1. rememberNavController(): Crea y recuerda el controlador de navegación en memoria.
 * 2. NavHost: Contenedor que mapea las rutas con cada composable de pantalla.
 * 3. navArgument: Especifica el nombre y tipo de dato (NavType.StringType) de cada parámetro.
 * 4. backStackEntry: Permite extraer los argumentos del Bundle con .arguments?.getString().
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.InicioBiblioteca.route
    ) {

        // ==========================================
        // FLUJO 1: BIBLIOTECA Y RESERVA INICIAL
        // ==========================================

        // 1. Inicio Biblioteca
        composable(route = AppScreens.InicioBiblioteca.route) {
            InicioScreen(navController = navController)
        }

        // 2. Lista de Libros
        composable(route = AppScreens.ListaLibros.route) {
            ListaLibrosScreen(navController = navController)
        }

        // 3. Detalle del Libro (1 argumento: nombreLibro)
        composable(
            route = AppScreens.DetalleLibro.route,
            arguments = listOf(
                navArgument("nombreLibro") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val nombreLibro = backStackEntry.arguments?.getString("nombreLibro") ?: ""
            DetalleLibroScreen(
                navController = navController,
                nombreLibro = nombreLibro
            )
        }

        // 4. Confirmación de Reserva (2 argumentos: nombreLibro y fechaReserva)
        composable(
            route = AppScreens.ConfirmacionReserva.route,
            arguments = listOf(
                navArgument("nombreLibro") {
                    type = NavType.StringType
                },
                navArgument("fechaReserva") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val nombreLibro = backStackEntry.arguments?.getString("nombreLibro") ?: ""
            val fechaReserva = backStackEntry.arguments?.getString("fechaReserva") ?: ""
            ConfirmacionScreen(
                navController = navController,
                nombreLibro = nombreLibro,
                fechaReserva = fechaReserva
            )
        }

        // ==========================================
        // FLUJO 2: GESTIÓN DE MIS RESERVAS
        // ==========================================

        // 5. Mis Reservas (Lista)
        composable(route = AppScreens.MisReservas.route) {
            MisReservasScreen(navController = navController)
        }

        // 6. Detalle de Reserva (3 argumentos: nombreLibro, fechaReserva, codigoReserva)
        composable(
            route = AppScreens.DetalleReserva.route,
            arguments = listOf(
                navArgument("nombreLibro") {
                    type = NavType.StringType
                },
                navArgument("fechaReserva") {
                    type = NavType.StringType
                },
                navArgument("codigoReserva") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val nombreLibro = backStackEntry.arguments?.getString("nombreLibro") ?: ""
            val fechaReserva = backStackEntry.arguments?.getString("fechaReserva") ?: ""
            val codigoReserva = backStackEntry.arguments?.getString("codigoReserva") ?: ""
            DetalleReservaScreen(
                navController = navController,
                nombreLibro = nombreLibro,
                fechaReserva = fechaReserva,
                codigoReserva = codigoReserva
            )
        }

        // 7. Renovar Reserva (3 argumentos: nombreLibro, fechaReserva, codigoReserva)
        composable(
            route = AppScreens.RenovarReserva.route,
            arguments = listOf(
                navArgument("nombreLibro") {
                    type = NavType.StringType
                },
                navArgument("fechaReserva") {
                    type = NavType.StringType
                },
                navArgument("codigoReserva") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val nombreLibro = backStackEntry.arguments?.getString("nombreLibro") ?: ""
            val fechaReserva = backStackEntry.arguments?.getString("fechaReserva") ?: ""
            val codigoReserva = backStackEntry.arguments?.getString("codigoReserva") ?: ""
            RenovarReservaScreen(
                navController = navController,
                nombreLibro = nombreLibro,
                fechaReserva = fechaReserva,
                codigoReserva = codigoReserva
            )
        }

        // 8. Renovación Exitosa (2 argumentos: nombreLibro y fechaNueva)
        composable(
            route = AppScreens.RenovacionExitosa.route,
            arguments = listOf(
                navArgument("nombreLibro") {
                    type = NavType.StringType
                },
                navArgument("fechaNueva") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val nombreLibro = backStackEntry.arguments?.getString("nombreLibro") ?: ""
            val fechaNueva = backStackEntry.arguments?.getString("fechaNueva") ?: ""
            RenovacionExitosaScreen(
                navController = navController,
                nombreLibro = nombreLibro,
                fechaNueva = fechaNueva
            )
        }
    }
}
