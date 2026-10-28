package com.sena.bibliotecaapp.ui.screens.reservas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sena.bibliotecaapp.data.model.Libro
import com.sena.bibliotecaapp.data.repository.BibliotecaRepository
import com.sena.bibliotecaapp.navigation.AppScreens
import com.sena.bibliotecaapp.ui.components.BookCoverView

/**
 * Pantalla 3 del Flujo 2: Renovar Reserva
 *
 * Explicación para evaluación:
 * - Formulario reactivo: El campo de "Nueva Fecha" utiliza mutableStateOf para permitir
 *   modificar la fecha o confirmarla.
 * - Al pulsar "Confirmar Renovación":
 *   1. Actualiza el repositorio en memoria con la nueva fecha.
 *   2. Envía la fecha seleccionada como parámetro a la pantalla de Renovación Exitosa.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenovarReservaScreen(
    navController: NavController,
    nombreLibro: String,
    fechaReserva: String,
    codigoReserva: String
) {
    val libroDecodificado = AppScreens.decode(nombreLibro)
    val fechaActualDecodificada = AppScreens.decode(fechaReserva)
    val codigoDecodificado = AppScreens.decode(codigoReserva)

    val libro: Libro = BibliotecaRepository.buscarLibroPorTitulo(libroDecodificado)
        ?: Libro(
            id = "0",
            titulo = libroDecodificado,
            autor = "Robert C. Martin",
            categoria = "Programación",
            descripcion = ""
        )

    // Estado reactivo para la nueva fecha (por defecto 30/06/2026 según el diagrama)
    var nuevaFecha by remember { mutableStateOf("30/06/2026") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Renovar Reserva", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Tarjeta resumen del libro a renovar
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BookCoverView(
                        titulo = libro.titulo,
                        colorFondo = Color(libro.colorCaratulaHex),
                        modifier = Modifier
                            .width(65.dp)
                            .height(90.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "Libro:",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                        Text(
                            text = libro.titulo,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Formulario de fechas
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    // Reserva Actual
                    Text(
                        text = "Reserva Actual:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF64748B)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF8FAFC), RoundedCornerShape(10.dp))
                            .padding(14.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = Color(0xFF64748B),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = fechaActualDecodificada,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF334155)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Nueva Fecha
                    Text(
                        text = "Nueva Fecha:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF64748B)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = nuevaFecha,
                        onValueChange = { nuevaFecha = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = Color(0xFFEA580C)
                            )
                        },
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Botón "Confirmar Renovación" (Naranja)
            Button(
                onClick = {
                    // Guardamos la renovación en el repositorio
                    BibliotecaRepository.renovarReserva(codigoDecodificado, nuevaFecha)
                    // Navegamos enviando el libro y la nueva fecha como argumentos
                    navController.navigate(
                        AppScreens.RenovacionExitosa.createRoute(
                            nombreLibro = libroDecodificado,
                            fechaNueva = nuevaFecha
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEA580C)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Confirmar Renovación",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
