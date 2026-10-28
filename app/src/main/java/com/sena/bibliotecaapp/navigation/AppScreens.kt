package com.sena.bibliotecaapp.navigation

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Jerarquía sellada (sealed class) que define todas las rutas y destinos de navegación en la app.
 *
 * ¿Por qué usamos una sealed class?
 * 1. Type Safety (Seguridad de tipos): Garantiza que sólo existan las pantallas definidas aquí.
 * 2. Mantenibilidad: Si cambia el nombre de una ruta, se cambia en un solo lugar y el compilador
 *    nos avisa dónde más se necesita ajustar.
 * 3. Funciones constructoras de rutas: Permite construir la ruta con parámetros y codificarlos
 *    correctamente para evitar errores con espacios o barras '/' (como en fechas "15/06/2026").
 */
sealed class AppScreens(val route: String) {

    // --- FLUJO 1: BIBLIOTECA Y RESERVA INICIAL ---

    // 1. Pantalla de bienvenida
    object InicioBiblioteca : AppScreens("inicio_biblioteca")

    // 2. Lista de libros disponibles
    object ListaLibros : AppScreens("lista_libros")

    // 3. Detalle del libro (Recibe 1 parámetro: nombreLibro)
    object DetalleLibro : AppScreens("detalle_libro/{nombreLibro}") {
        fun createRoute(nombreLibro: String): String {
            val encodedNombre = URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8.toString())
            return "detalle_libro/$encodedNombre"
        }
    }

    // 4. Confirmación de reserva inicial (Recibe 2 parámetros: nombreLibro y fechaReserva)
    object ConfirmacionReserva : AppScreens("confirmacion_reserva/{nombreLibro}/{fechaReserva}") {
        fun createRoute(nombreLibro: String, fechaReserva: String): String {
            val encNombre = URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8.toString())
            val encFecha = URLEncoder.encode(fechaReserva, StandardCharsets.UTF_8.toString())
            return "confirmacion_reserva/$encNombre/$encFecha"
        }
    }

    // --- FLUJO 2: GESTIÓN DE MIS RESERVAS ---

    // 5. Lista de reservas activas
    object MisReservas : AppScreens("mis_reservas")

    // 6. Detalle de una reserva (Recibe 3 parámetros: nombreLibro, fechaReserva, codigoReserva)
    object DetalleReserva : AppScreens("detalle_reserva/{nombreLibro}/{fechaReserva}/{codigoReserva}") {
        fun createRoute(nombreLibro: String, fechaReserva: String, codigoReserva: String): String {
            val encNombre = URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8.toString())
            val encFecha = URLEncoder.encode(fechaReserva, StandardCharsets.UTF_8.toString())
            val encCodigo = URLEncoder.encode(codigoReserva, StandardCharsets.UTF_8.toString())
            return "detalle_reserva/$encNombre/$encFecha/$encCodigo"
        }
    }

    // 7. Formulario para renovar reserva (Recibe nombreLibro, fechaReserva, codigoReserva)
    object RenovarReserva : AppScreens("renovar_reserva/{nombreLibro}/{fechaReserva}/{codigoReserva}") {
        fun createRoute(nombreLibro: String, fechaReserva: String, codigoReserva: String): String {
            val encNombre = URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8.toString())
            val encFecha = URLEncoder.encode(fechaReserva, StandardCharsets.UTF_8.toString())
            val encCodigo = URLEncoder.encode(codigoReserva, StandardCharsets.UTF_8.toString())
            return "renovar_reserva/$encNombre/$encFecha/$encCodigo"
        }
    }

    // 8. Confirmación de renovación exitosa (Recibe 2 parámetros: nombreLibro y fechaNueva)
    object RenovacionExitosa : AppScreens("renovacion_exitosa/{nombreLibro}/{fechaNueva}") {
        fun createRoute(nombreLibro: String, fechaNueva: String): String {
            val encNombre = URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8.toString())
            val encFecha = URLEncoder.encode(fechaNueva, StandardCharsets.UTF_8.toString())
            return "renovacion_exitosa/$encNombre/$encFecha"
        }
    }

    companion object {
        /**
         * Función auxiliar para decodificar cualquier argumento recibido en la pantalla.
         */
        fun decode(param: String?): String {
            return if (param != null) {
                URLDecoder.decode(param, StandardCharsets.UTF_8.toString())
            } else {
                ""
            }
        }
    }
}
