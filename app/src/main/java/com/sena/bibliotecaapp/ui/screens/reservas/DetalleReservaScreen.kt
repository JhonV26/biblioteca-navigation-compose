package com.sena.bibliotecaapp.ui.screens.reservas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
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
import com.sena.bibliotecaapp.ui.components.InfoBadge
import com.sena.bibliotecaapp.ui.screens.libros.FilaDetalle

/**
 * Pantalla 2 del Flujo 2: Detalle de Reserva
 *
 * Explicación para evaluación:
 * - Recepción de múltiples argumentos: Recibe nombreLibro, fechaReserva y codigoReserva.
 * - Acciones:
 *   1. Renovar Reserva -> Abre la pantalla de renovación para cambiar la fecha.
 *   2. Cancelar Reserva -> Invoca la eliminación en el repositorio y hace popBackStack().
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleReservaScreen(
    navController: NavController,
    nombreLibro: String,
    fechaReserva: String,
    codigoReserva: String
) {
    val libroDecodificado = AppScreens.decode(nombreLibro)
    val fechaDecodificada = AppScreens.decode(fechaReserva)
    val codigoDecodificado = AppScreens.decode(codigoReserva)

    val libro: Libro = BibliotecaRepository.buscarLibroPorTitulo(libroDecodificado)
        ?: Libro(
            id = "0",
            titulo = libroDecodificado,
            autor = "Robert C. Martin",
            categoria = "Programación",
            descripcion = ""
        )

    var mostrarDialogoCancelar by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Reserva", fontWeight = FontWeight.Bold) },
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

            // Indicador visual verde "Argumentos recibidos"
            InfoBadge(
                texto = "Argumentos recibidos",
                icono = Icons.Default.CheckCircle,
                colorFondo = Color(0xFFDCFCE7),
                colorTexto = Color(0xFF16A34A)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Tarjeta contenedora con la información recibida
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BookCoverView(
                            titulo = libro.titulo,
                            colorFondo = Color(libro.colorCaratulaHex),
                            modifier = Modifier
                                .width(85.dp)
                                .height(120.dp)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = "Nombre:",
                                fontSize = 12.sp,
                                color = Color(0xFF64748B)
                            )
                            Text(
                                text = libro.titulo,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Autor:",
                                fontSize = 12.sp,
                                color = Color(0xFF64748B)
                            )
                            Text(
                                text = libro.autor,
                                fontSize = 14.sp,
                                color = Color(0xFF334155)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = Color(0xFFE2E8F0))
                    Spacer(modifier = Modifier.height(14.dp))

                    FilaDetalle(etiqueta = "Fecha Reserva:", valor = fechaDecodificada)
                    Spacer(modifier = Modifier.height(8.dp))
                    FilaDetalle(etiqueta = "Código Reserva:", valor = codigoDecodificado)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Estado:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF334155)
                        )
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFDCFCE7)
                        ) {
                            Text(
                                text = "Activa",
                                color = Color(0xFF16A34A),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mensaje explicativo pedagógico
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color(0xFF3B82F6),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Estos datos fueron enviados desde la pantalla anterior como parámetros.",
                        fontSize = 12.sp,
                        color = Color(0xFF475569)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Botones de acción en fila
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Botón "Renovar Reserva" (Verde)
                Button(
                    onClick = {
                        navController.navigate(
                            AppScreens.RenovarReserva.createRoute(
                                nombreLibro = libroDecodificado,
                                fechaReserva = fechaDecodificada,
                                codigoReserva = codigoDecodificado
                            )
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF16A34A)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Autorenew,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Renovar Reserva",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Botón "Cancelar Reserva" (Rojo)
                Button(
                    onClick = { mostrarDialogoCancelar = true },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFDC2626)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Cancelar Reserva",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Diálogo de confirmación para cancelar
        if (mostrarDialogoCancelar) {
            AlertDialog(
                onDismissRequest = { mostrarDialogoCancelar = false },
                title = { Text("Cancelar Reserva") },
                text = { Text("¿Estás seguro de que deseas cancelar la reserva de '$libroDecodificado'?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            mostrarDialogoCancelar = false
                            BibliotecaRepository.cancelarReserva(codigoDecodificado)
                            navController.popBackStack()
                        }
                    ) {
                        Text("Sí, Cancelar", color = Color(0xFFDC2626), fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogoCancelar = false }) {
                        Text("No")
                    }
                }
            )
        }
    }
}
