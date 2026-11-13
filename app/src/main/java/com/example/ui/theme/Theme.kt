package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = BwDarkPrimary,
    onPrimary = BwDarkOnPrimary,
    primaryContainer = BwDarkSurfaceVariant,
    onPrimaryContainer = BwDarkOnSurface,
    secondary = BwDarkOnSurfaceMuted,
    onSecondary = BwDarkOnPrimary,
    secondaryContainer = BwDarkSurfaceVariant,
    onSecondaryContainer = BwDarkOnSurface,
    tertiary = BwDarkOnSurfaceMuted,
    onTertiary = BwDarkOnPrimary,
    tertiaryContainer = BwDarkSurfaceVariant,
    onTertiaryContainer = BwDarkOnSurface,
    background = BwDarkBackground,
    onBackground = BwDarkOnSurface,
    surface = BwDarkSurface,
    onSurface = BwDarkOnSurface,
    surfaceVariant = BwDarkSurfaceVariant,
    onSurfaceVariant = BwDarkOnSurfaceMuted,
    outline = BwDarkOutline,
    error = BwDarkOnSurface,
    onError = BwDarkOnPrimary
)

private val LightColorScheme = lightColorScheme(
    primary = BwLightPrimary,
    onPrimary = BwLightOnPrimary,
    primaryContainer = BwLightSurfaceVariant,
    onPrimaryContainer = BwLightOnSurface,
    secondary = BwLightOnSurfaceMuted,
    onSecondary = BwLightOnPrimary,
    secondaryContainer = BwLightSurfaceVariant,
    onSecondaryContainer = BwLightOnSurface,
    tertiary = BwLightOnSurfaceMuted,
    onTertiary = BwLightOnPrimary,
    tertiaryContainer = BwLightSurfaceVariant,
    onTertiaryContainer = BwLightOnSurface,
    background = BwLightBackground,
    onBackground = BwLightOnSurface,
    surface = BwLightSurface,
    onSurface = BwLightOnSurface,
    surfaceVariant = BwLightSurfaceVariant,
    onSurfaceVariant = BwLightOnSurfaceMuted,
    outline = BwLightOutline,
    error = BwLightOnSurface,
    onError = BwLightOnPrimary
)

val WebAmoozShapes = Shapes(
    small = RoundedCornerShape(6.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(10.dp),
    extraLarge = RoundedCornerShape(12.dp)
)

enum class ThemePreference {
    SYSTEM, DARK, LIGHT
}

@Composable
fun WebAmoozTheme(
    themePreference: ThemePreference = ThemePreference.LIGHT,
    content: @Composable () -> Unit
) {
    val isDark = when (themePreference) {
        ThemePreference.SYSTEM -> isSystemInDarkTheme()
        ThemePreference.DARK -> true
        ThemePreference.LIGHT -> false
    }

    MaterialTheme(
        colorScheme = if (isDark) DarkColorScheme else LightColorScheme,
        typography = WebAmoozTypography,
        shapes = WebAmoozShapes,
        content = content
    )
}
