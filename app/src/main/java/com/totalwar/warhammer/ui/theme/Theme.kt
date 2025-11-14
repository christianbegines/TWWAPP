package com.totalwar.warhammer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    onSecondary = ColorOnPrimary,
    onBackground = PrimaryColor // Ahora los textos por defecto usan primary
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    onSecondary = ColorOnPrimary,
)

@Composable
fun TotalWarhammerAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography, // Asegúrate de que Typography está actualizado a M3
        shapes = Shapes,         // Igual para Shapes si los usas
        content = content
    )
}
