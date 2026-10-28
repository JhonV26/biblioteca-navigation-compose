package com.sena.bibliotecaapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Chip o Etiqueta destacada para mostrar parámetros recibidos o estados.
 *
 * ¿Por qué crear componentes reutilizables?
 * Para no duplicar código visual y mantener consistencia en todo el diseño
 * (Principio DRY: Don't Repeat Yourself).
 */
@Composable
fun InfoBadge(
    texto: String,
    icono: ImageVector = Icons.Default.Info,
    colorFondo: Color = Color(0xFFFEF3C7), // Ámbar suave
    colorTexto: Color = Color(0xFFD97706)  // Ámbar fuerte
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = colorFondo,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = colorTexto,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = texto,
                color = colorTexto,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

/**
 * Representación visual simulada de la portada de un libro.
 */
@Composable
fun BookCoverView(
    titulo: String,
    autor: String = "",
    colorFondo: Color = Color(0xFF0F172A),
    modifier: Modifier = Modifier
        .width(130.dp)
        .height(180.dp)
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(colorFondo)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Book,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = titulo,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            if (autor.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = autor,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 11.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}
