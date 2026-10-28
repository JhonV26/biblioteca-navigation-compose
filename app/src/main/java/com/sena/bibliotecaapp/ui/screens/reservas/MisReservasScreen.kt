package com.sena.bibliotecaapp.ui.screens.reservas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sena.bibliotecaapp.data.model.Reserva
import com.sena.bibliotecaapp.data.repository.BibliotecaRepository
import com.sena.bibliotecaapp.navigation.AppScreens
import com.sena.bibliotecaapp.ui.components.BookCoverView

/**
 * Pantalla 1 del Flujo 2: Mis Reservas
 *
 * Explicación para evaluación:
 * - Envío de múltiples parámetros: Al pulsar "Ver Reserva", empaquetamos:
 *   1. nombreLibro
 *   2. fechaReserva
 *   3. codigoReserva
 *   Esto demuestra cómo enviar más de un argumento a través de la URL de navegación.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisReservasScreen(navController: NavController) {
    // Obtenemos la lista reactiva de reservas
    val reservas = remember { mutableStateListOf<Reserva>() }

    // Sincronizamos con el repositorio
    LaunchedEffect(Unit) {
        reservas.clear()
        reservas.addAll(BibliotecaRepository.obtenerReservas())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Reservas", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {
                        // Vuelve a la pantalla anterior o al inicio
                        if (!navController.popBackStack()) {
                            navController.navigate(AppScreens.InicioBiblioteca.route)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.Book, contentDescription = "Libros")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Subtítulo
            Text(
                text = "Lista de reservas activas",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF334155)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (reservas.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No tienes reservas activas en este momento.",
                        color = Color(0xFF64748B),
                        fontSize = 15.sp
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(reservas) { reserva ->
                        CardReservaItem(
                            reserva = reserva,
                            onVerReserva = {
                                // Navegamos enviando los 3 argumentos que pide la práctica
                                navController.navigate(
                                    AppScreens.DetalleReserva.createRoute(
                                        nombreLibro = reserva.libro.titulo,
                                        fechaReserva = reserva.fechaReserva,
                                        codigoReserva = reserva.codigoReserva
                                    )
                                )
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Banner inferior de información
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color(0xFF4F46E5),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Aquí verás todas tus reservas activas.",
                        fontSize = 13.sp,
                        color = Color(0xFF475569)
                    )
                }
            }
        }
    }
}

@Composable
fun CardReservaItem(
    reserva: Reserva,
    onVerReserva: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Miniatura de portada
                BookCoverView(
                    titulo = reserva.libro.titulo,
                    colorFondo = Color(reserva.libro.colorCaratulaHex),
                    modifier = Modifier
                        .width(70.dp)
                        .height(95.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Libro:",
                        fontSize = 11.sp,
                        color = Color(0xFF64748B)
                    )
                    Text(
                        text = reserva.libro.titulo,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Fecha: ${reserva.fechaReserva}",
                        fontSize = 13.sp,
                        color = Color(0xFF475569)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Chip de estado ("Activa" en verde suave)
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (reserva.estado == "Activa") Color(0xFFDCFCE7) else Color(0xFFFEF3C7)
                    ) {
                        Text(
                            text = reserva.estado,
                            color = if (reserva.estado == "Activa") Color(0xFF16A34A) else Color(0xFFD97706),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Botón "Ver Reserva" (Morado índigo)
            Button(
                onClick = onVerReserva,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4F46E5)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Visibility,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ver Reserva",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
