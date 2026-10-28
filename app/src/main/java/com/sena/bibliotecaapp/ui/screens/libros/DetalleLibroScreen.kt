package com.sena.bibliotecaapp.ui.screens.libros

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

/**
 * Pantalla 3: Detalle del Libro
 *
 * Explicación para evaluación:
 * - nombreLibro: Argumento extraído de NavBackStackEntry en AppNavigation.
 * - Decodificación: Si contiene espacios, AppScreens.decode() lo restaura a su forma original.
 * - Repositorio: Con el título obtenido, consultamos BibliotecaRepository.buscarLibroPorTitulo().
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleLibroScreen(
    navController: NavController,
    nombreLibro: String
) {
    val tituloDecodificado = AppScreens.decode(nombreLibro)
    val libro: Libro = BibliotecaRepository.buscarLibroPorTitulo(tituloDecodificado)
        ?: Libro(
            id = "0",
            titulo = tituloDecodificado,
            autor = "Autor no disponible",
            categoria = "General",
            descripcion = "Sin descripción disponible para este ejemplar."
        )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Libro", fontWeight = FontWeight.Bold) },
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

            // Indicador visual de parámetro recibido
            InfoBadge(
                texto = "Parámetro recibido: $tituloDecodificado",
                icono = Icons.Default.Download,
                colorFondo = Color(0xFFFEF3C7),
                colorTexto = Color(0xFFD97706)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Portada visual del libro
            BookCoverView(
                titulo = libro.titulo,
                autor = libro.autor,
                colorFondo = Color(libro.colorCaratulaHex),
                modifier = Modifier
                    .width(140.dp)
                    .height(190.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Ficha técnica del libro
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    FilaDetalle(etiqueta = "Nombre:", valor = libro.titulo, resaltar = true)
                    Spacer(modifier = Modifier.height(8.dp))
                    FilaDetalle(etiqueta = "Autor:", valor = libro.autor)
                    Spacer(modifier = Modifier.height(8.dp))
                    FilaDetalle(etiqueta = "Categoría:", valor = libro.categoria, colorValor = Color(0xFF2563EB))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Descripción:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF1E293B)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = libro.descripcion,
                        fontSize = 13.sp,
                        color = Color(0xFF475569),
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Botón de acción: Reservar Libro
            Button(
                onClick = {
                    val fechaHoy = "15/06/2026"
                    // Registramos en el repositorio
                    BibliotecaRepository.registrarReserva(libro.titulo, fechaHoy)
                    // Navegamos a la pantalla de confirmación pasando el nombre y la fecha
                    navController.navigate(
                        AppScreens.ConfirmacionReserva.createRoute(libro.titulo, fechaHoy)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEA580C) // Naranja vivo según el diseño
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Reservar Libro",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun FilaDetalle(
    etiqueta: String,
    valor: String,
    resaltar: Boolean = false,
    colorValor: Color = Color(0xFF1E293B)
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = etiqueta,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF334155)
        )
        Text(
            text = valor,
            fontSize = 14.sp,
            fontWeight = if (resaltar) FontWeight.Bold else FontWeight.Normal,
            color = colorValor
        )
    }
}
