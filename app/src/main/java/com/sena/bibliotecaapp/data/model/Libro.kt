package com.sena.bibliotecaapp.data.model

/**
 * Modelo inmutable para representar un Libro en la biblioteca.
 *
 * ¿Por qué data class?
 * En Kotlin, una "data class" genera de forma automática:
 * - toString(): para depuración e impresión legible.
 * - equals() / hashCode(): para comparar libros por sus valores y no por su referencia en memoria.
 * - copy(): para crear copias modificando únicamente los campos deseados.
 */
data class Libro(
    val id: String,
    val titulo: String,
    val autor: String,
    val categoria: String,
    val descripcion: String,
    val colorCaratulaHex: Long = 0xFF1E293B // Color representativo para el placeholder del libro
)
