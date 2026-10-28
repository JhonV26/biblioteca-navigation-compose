package com.sena.bibliotecaapp.data.repository

import com.sena.bibliotecaapp.data.model.Libro
import com.sena.bibliotecaapp.data.model.Reserva

/**
 * Repositorio centralizado (Patrón Repository implementado como Singleton en Kotlin).
 *
 * ¿Por qué usamos "object" en lugar de "class"?
 * En Kotlin, la palabra reservada "object" crea un Singleton de forma nativa.
 * Garantiza que exista una ÚNICA instancia en memoria durante toda la ejecución de la app,
 * permitiendo que las reservas creadas o renovadas en una pantalla se conserven
 * y estén disponibles en todas las demás pantallas.
 */
object BibliotecaRepository {

    // Lista fija de libros disponibles según la guía práctica
    private val listaLibros: List<Libro> = listOf(
        Libro(
            id = "1",
            titulo = "Clean Code",
            autor = "Robert C. Martin",
            categoria = "Programación",
            descripcion = "Guía práctica para escribir código limpio, legible y mantenible. Un clásico imprescindible para desarrolladores.",
            colorCaratulaHex = 0xFF0F172A // Azul noche
        ),
        Libro(
            id = "2",
            titulo = "Kotlin para Android",
            autor = "Antonio Leiva",
            categoria = "Desarrollo Móvil",
            descripcion = "Aprende a desarrollar aplicaciones Android modernas utilizando el poder y concisión de Kotlin de principio a fin.",
            colorCaratulaHex = 0xFF15803D // Verde Android
        ),
        Libro(
            id = "3",
            titulo = "Arquitectura de Software",
            autor = "Martin Fowler",
            categoria = "Arquitectura",
            descripcion = "Patrones esenciales y fundamentos para diseñar arquitecturas de software empresariales escalables y desacopladas.",
            colorCaratulaHex = 0xFFC2410C // Naranja
        ),
        Libro(
            id = "4",
            titulo = "Jetpack Compose",
            autor = "Google Developers",
            categoria = "UI Android",
            descripcion = "El kit de herramientas moderno de Android para construir interfaces de usuario nativas de forma completamente declarativa y reactiva.",
            colorCaratulaHex = 0xFF0284C7 // Cian Compose
        )
    )

    // Lista mutable en memoria para gestionar las reservas activas
    private val listaReservas: MutableList<Reserva> = mutableListOf(
        Reserva(
            codigoReserva = "RES-2026-001",
            libro = listaLibros[0], // Clean Code
            fechaReserva = "15/06/2026",
            estado = "Activa"
        )
    )

    /**
     * Retorna la lista inmutable de todos los libros disponibles.
     */
    fun obtenerLibros(): List<Libro> = listaLibros

    /**
     * Busca un libro por su título exacto o insensible a mayúsculas.
     * Retorna un Libro? (Nullable): Si existe devuelve el Libro, si no devuelve null.
     */
    fun buscarLibroPorTitulo(titulo: String): Libro? {
        return listaLibros.find { it.titulo.equals(titulo, ignoreCase = true) }
    }

    /**
     * Retorna la lista actual de reservas.
     */
    fun obtenerReservas(): List<Reserva> = listaReservas.toList()

    /**
     * Busca una reserva por su código único.
     */
    fun buscarReservaPorCodigo(codigo: String): Reserva? {
        return listaReservas.find { it.codigoReserva.equals(codigo, ignoreCase = true) }
    }

    /**
     * Registra una nueva reserva para un libro determinado.
     */
    fun registrarReserva(tituloLibro: String, fecha: String = "15/06/2026"): Reserva {
        val libro = buscarLibroPorTitulo(tituloLibro) ?: listaLibros.first()
        val nuevoCodigo = "RES-2026-00${listaReservas.size + 1}"
        val nuevaReserva = Reserva(
            codigoReserva = nuevoCodigo,
            libro = libro,
            fechaReserva = fecha,
            estado = "Activa"
        )
        listaReservas.add(nuevaReserva)
        return nuevaReserva
    }

    /**
     * Actualiza la fecha de una reserva existente y marca su estado como "Renovada".
     */
    fun renovarReserva(codigoReserva: String, nuevaFecha: String): Boolean {
        val index = listaReservas.indexOfFirst { it.codigoReserva == codigoReserva }
        return if (index != -1) {
            val actual = listaReservas[index]
            listaReservas[index] = actual.copy(
                fechaNueva = nuevaFecha,
                estado = "Renovada"
            )
            true
        } else {
            false
        }
    }

    /**
     * Cancela una reserva eliminándola o cambiando su estado a "Cancelada".
     */
    fun cancelarReserva(codigoReserva: String): Boolean {
        val index = listaReservas.indexOfFirst { it.codigoReserva == codigoReserva }
        return if (index != -1) {
            listaReservas.removeAt(index)
            true
        } else {
            false
        }
    }
}
