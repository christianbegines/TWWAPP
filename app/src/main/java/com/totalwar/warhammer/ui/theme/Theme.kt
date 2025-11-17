package com.totalwar.warhammer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Color scheme for dark mode
 * Optimized for Total Warhammer theme
 */
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    onPrimary = ColorOnPrimary,
    primaryContainer = PrimaryDarkColor,
    onPrimaryContainer = ColorOnPrimary,
    secondary = SecondaryColor,
    onSecondary = ColorOnSecondary,
    secondaryContainer = SecondaryDarkColor,
    onSecondaryContainer = ColorOnSecondary,
    background = PrimaryColor,
    onBackground = ColorOnPrimary,
    surface = SecondaryColor,
    onSurface = ColorOnPrimary,
    error = BulletDecrease,
    onError = ColorOnPrimary,
)

/**
 * Color scheme for light mode
 * Similar to dark mode to maintain brand consistency
 */
private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = ColorOnPrimary,
    primaryContainer = PrimaryColor,
    onPrimaryContainer = ColorOnPrimary,
    secondary = SecondaryColor,
    onSecondary = ColorOnSecondary,
    secondaryContainer = SecondaryColor,
    onSecondaryContainer = ColorOnSecondary,
    background = PrimaryColor,
    onBackground = ColorOnPrimary,
    surface = SecondaryColor,
    onSurface = ColorOnPrimary,
    error = BulletDecrease,
    onError = ColorOnPrimary,
)

/**
 * Main theme composable for the Total Warhammer application
 * Uses Material Design 3 with custom colors
 * @param darkTheme Whether to use dark theme (default true)
 * @param content The content to display with this theme
 */
@Composable
fun TotalWarhammerAppTheme(
    darkTheme: Boolean = true, // Always use dark theme for this app
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
