package com.sena.bibliotecaapp.data.model

/**
 * Modelo de datos para representar una Reserva activa o modificada en la biblioteca.
 *
 * Parametros que viajan en la navegación según los diagramas:
 * - nombreLibro: Nombre del libro reservado.
 * - fechaReserva: Fecha en la que se efectuó la reserva inicial (ej. 15/06/2026).
 * - codigoReserva: Identificador único de la reserva (ej. RES-2026-001).
 * - estado: Estado de la reserva ("Activa", "Renovada", "Cancelada").
 * - fechaNueva: Nueva fecha asignada al renovar (ej. 30/06/2026).
 */
data class Reserva(
    val codigoReserva: String,
    val libro: Libro,
    val fechaReserva: String,
    var fechaNueva: String? = null,
    var estado: String = "Activa"
)
