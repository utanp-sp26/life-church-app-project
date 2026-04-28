package edu.utap.life_church_app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = LifePrimary,
    onPrimary = LifePrimaryForeground,
    primaryContainer = LifeInputBackground,
    onPrimaryContainer = LifePrimary,
    secondary = LifeSecondary,
    onSecondary = LifeSecondaryForeground,
    secondaryContainer = LifeMuted,
    onSecondaryContainer = LifePrimary,
    tertiary = LifeAccent,
    onTertiary = LifeAccentForeground,
    tertiaryContainer = LifeMuted,
    onTertiaryContainer = LifeMutedForeground,
    error = LifeDestructive,
    onError = LifeDestructiveForeground,
    errorContainer = LifeDestructive.copy(alpha = 0.12f),
    onErrorContainer = LifeDestructive,
    background = LifeBackground,
    onBackground = LifeForeground,
    surface = LifeCard,
    onSurface = LifeCardForeground,
    surfaceVariant = LifeMuted,
    onSurfaceVariant = LifeMutedForeground,
    outline = LifeRing,
    outlineVariant = LifeBorder,
    surfaceTint = LifePrimary
)

private val DarkColorScheme = darkColorScheme(
    primary = LifeDarkPrimary,
    onPrimary = LifeDarkPrimaryForeground,
    primaryContainer = LifeDarkSecondary,
    onPrimaryContainer = LifeDarkForeground,
    secondary = LifeDarkSecondary,
    onSecondary = LifeDarkForeground,
    secondaryContainer = LifeDarkMuted,
    onSecondaryContainer = LifeDarkMutedForeground,
    tertiary = LifeDarkAccent,
    onTertiary = LifeDarkForeground,
    tertiaryContainer = LifeDarkMuted,
    onTertiaryContainer = LifeDarkMutedForeground,
    error = LifeDarkDestructive,
    onError = LifeDarkForeground,
    errorContainer = LifeDarkDestructive.copy(alpha = 0.25f),
    onErrorContainer = LifeDarkForeground,
    background = LifeDarkBackground,
    onBackground = LifeDarkForeground,
    surface = LifeDarkCard,
    onSurface = LifeDarkForeground,
    surfaceVariant = LifeDarkMuted,
    onSurfaceVariant = LifeDarkMutedForeground,
    outline = LifeDarkBorder,
    outlineVariant = LifeDarkBorder.copy(alpha = 0.5f),
    surfaceTint = LifeDarkPrimary
)

@Composable
fun Life_church_appTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    /** Off by default so the fixed Figma / `theme.css` palette is not overridden by Android 12+ dynamic color. */
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = LifeShapes,
        content = content
    )
}
