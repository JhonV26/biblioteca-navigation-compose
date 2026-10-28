package com.sena.bibliotecaapp.ui.screens.libros

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sena.bibliotecaapp.data.model.Libro
import com.sena.bibliotecaapp.data.repository.BibliotecaRepository
import com.sena.bibliotecaapp.navigation.AppScreens

/**
 * Pantalla 2: Lista de Libros
 *
 * Explicación para evaluación:
 * - LazyColumn: Es el equivalente reactivo del RecyclerView. Sólo renderiza los elementos
 *   visibles en pantalla, ahorrando memoria y garantizando fluidez.
 * - Enviar parámetros: Al hacer clic en "Ver Detalle", pasamos el título del libro en la ruta:
 *   navController.navigate(AppScreens.DetalleLibro.createRoute(libro.titulo))
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaLibrosScreen(navController: NavController) {
    val libros: List<Libro> = BibliotecaRepository.obtenerLibros()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Libros", fontWeight = FontWeight.Bold) },
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
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Encabezado
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AutoStories,
                    contentDescription = null,
                    tint = Color(0xFF4F46E5),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Mis Libros",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Elige un libro para ver más detalles.",
                fontSize = 14.sp,
                color = Color(0xFF64748B)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lista reactiva de libros
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(libros) { libro ->
                    CardLibroItem(
                        libro = libro,
                        onVerDetalle = {
                            // Navegación con envío de parámetro
                            navController.navigate(AppScreens.DetalleLibro.createRoute(libro.titulo))
                        }
                    )
                }
            }
        }
    }
}

/**
 * Componente de tarjeta para cada fila de libro en la lista
 */
@Composable
fun CardLibroItem(
    libro: Libro,
    onVerDetalle: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono con color personalizado según el libro
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(libro.colorCaratulaHex)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Book,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Título del libro
            Text(
                text = libro.titulo,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B),
                modifier = Modifier.weight(1f)
            )

            // Botón "Ver Detalle"
            OutlinedButton(
                onClick = onVerDetalle,
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Ver Detalle",
                    fontSize = 13.sp,
                    color = Color(0xFF4F46E5),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
