package com.sena.bibliotecaapp.ui.screens.inicio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sena.bibliotecaapp.navigation.AppScreens

/**
 * Pantalla 1: Inicio de la Biblioteca
 *
 * Explicación para evaluación:
 * - navController: Es el controlador que orquesta el cambio de pantalla.
 * - Button con onClick: Dispara la navegación hacia la ruta de la lista de libros.
 */
@Composable
fun InicioScreen(navController: NavController) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Contenedor circular con icono de biblioteca
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEEF2FF)), // Azul/violeta muy suave
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AutoStories,
                    contentDescription = "Icono Biblioteca",
                    tint = Color(0xFF4338CA), // Azul índigo
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Título
            Text(
                text = "¡Bienvenido a tu\nBiblioteca!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF1E293B),
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Subtítulo
            Text(
                text = "Descubre, aprende y reserva tus libros favoritos.",
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF64748B)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Botón principal "Ver Libros"
            Button(
                onClick = {
                    // Navega hacia la pantalla de Lista de Libros
                    navController.navigate(AppScreens.ListaLibros.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4F46E5) // Púrpura índigo
                )
            ) {
                Icon(
                    imageVector = Icons.Default.AutoStories,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ver Libros",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón secundario para acceder directo a "Mis Reservas"
            OutlinedButton(
                onClick = {
                    navController.navigate(AppScreens.MisReservas.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ir a Mis Reservas",
                    fontSize = 15.sp
                )
            }
        }
    }
}
