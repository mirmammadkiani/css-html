package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = NotionDarkPrimary,
    onPrimary = NotionDarkOnPrimary,
    primaryContainer = NotionDarkSurfaceVariant,
    onPrimaryContainer = NotionDarkOnSurface,
    secondary = NotionDarkOnSurfaceMuted,
    onSecondary = NotionDarkOnPrimary,
    secondaryContainer = NotionDarkSurfaceVariant,
    onSecondaryContainer = NotionDarkOnSurface,
    tertiary = NotionDarkOnSurfaceMuted,
    onTertiary = NotionDarkOnPrimary,
    tertiaryContainer = NotionDarkSurfaceVariant,
    onTertiaryContainer = NotionDarkOnSurface,
    background = NotionDarkBackground,
    onBackground = NotionDarkOnSurface,
    surface = NotionDarkSurface,
    onSurface = NotionDarkOnSurface,
    surfaceVariant = NotionDarkSurfaceVariant,
    onSurfaceVariant = NotionDarkOnSurfaceMuted,
    outline = NotionDarkOutline,
    error = Color(0xFFD4D4D8),
    onError = NotionDarkOnPrimary
)

private val LightColorScheme = lightColorScheme(
    primary = NotionLightPrimary,
    onPrimary = NotionLightOnPrimary,
    primaryContainer = NotionLightSurfaceVariant,
    onPrimaryContainer = NotionLightOnSurface,
    secondary = NotionLightOnSurfaceMuted,
    onSecondary = NotionLightOnPrimary,
    secondaryContainer = NotionLightSurfaceVariant,
    onSecondaryContainer = NotionLightOnSurface,
    tertiary = NotionLightOnSurfaceMuted,
    onTertiary = NotionLightOnPrimary,
    tertiaryContainer = NotionLightSurfaceVariant,
    onTertiaryContainer = NotionLightOnSurface,
    background = NotionLightBackground,
    onBackground = NotionLightOnSurface,
    surface = NotionLightSurface,
    onSurface = NotionLightOnSurface,
    surfaceVariant = NotionLightSurfaceVariant,
    onSurfaceVariant = NotionLightOnSurfaceMuted,
    outline = NotionLightOutline,
    error = Color(0xFF37352F),
    onError = NotionLightOnPrimary
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
fun isThemeDark(themePreference: ThemePreference): Boolean = when (themePreference) {
    ThemePreference.SYSTEM -> isSystemInDarkTheme()
    ThemePreference.DARK -> true
    ThemePreference.LIGHT -> false
}

@Composable
fun WebAmoozTheme(
    themePreference: ThemePreference = ThemePreference.DARK,
    content: @Composable () -> Unit
) {
    val isDark = isThemeDark(themePreference)

    MaterialTheme(
        colorScheme = if (isDark) DarkColorScheme else LightColorScheme,
        typography = WebAmoozTypography,
        shapes = WebAmoozShapes,
        content = content
    )
}
