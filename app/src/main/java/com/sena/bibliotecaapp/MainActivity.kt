package com.sena.bibliotecaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sena.bibliotecaapp.navigation.AppNavigation
import com.sena.bibliotecaapp.ui.theme.BibliotecaAppTheme

/**
 * MainActivity: Punto de entrada único (Single Activity Pattern) en Compose.
 *
 * Explicación para evaluación:
 * - Single Activity Pattern: En aplicaciones modernas con Jetpack Compose no se crea una
 *   Activity para cada pantalla (eso era en Android tradicional con XML). Ahora tenemos
 *   una ÚNICA Activity que contiene el NavHost, y las pantallas son funciones @Composable
 *   que se renderizan dinámicamente. Esto ahorra memoria y optimiza el ciclo de vida.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BibliotecaAppTheme {
                AppNavigation()
            }
        }
    }
}
