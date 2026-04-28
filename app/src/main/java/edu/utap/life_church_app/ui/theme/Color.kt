package edu.utap.life_church_app.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Design tokens aligned with the web app `src/styles/theme.css` (`:root` and `.dark`).
 * oklch() values are approximated to sRGB hex for Compose.
 */

// --- :root (light) — exact hex from CSS where provided ---
val LifeBackground = Color(0xFFFFFFFF) // --background
val LifeForeground = Color(0xFF252525) // --foreground oklch(0.145 0 0)
val LifeCard = Color(0xFFFFFFFF) // --card
val LifeCardForeground = LifeForeground
val LifePrimary = Color(0xFF030213) // --primary
val LifePrimaryForeground = Color(0xFFFFFFFF) // --primary-foreground
val LifeSecondary = Color(0xFFF2F2F5) // --secondary oklch(0.95 0.0058 264.53)
val LifeSecondaryForeground = LifePrimary
val LifeMuted = Color(0xFFECECF0) // --muted
val LifeMutedForeground = Color(0xFF717182) // --muted-foreground
val LifeAccent = Color(0xFFE9EBEF) // --accent
val LifeAccentForeground = LifePrimary
val LifeDestructive = Color(0xFFD4183D) // --destructive
val LifeDestructiveForeground = Color(0xFFFFFFFF)
val LifeBorder = Color(0x1A000000) // --border rgba(0,0,0,0.1)
val LifeInputBackground = Color(0xFFF3F3F5) // --input-background
val LifeSwitchBackground = Color(0xFFCBCED4) // --switch-background
val LifeRing = Color(0xFFB0B0B0) // --ring oklch(0.708) approx

// --- .dark — oklch approximations to sRGB ---
val LifeDarkBackground = Color(0xFF252525) // --background
val LifeDarkForeground = Color(0xFFFAFAFA) // --foreground oklch(0.985)
val LifeDarkCard = Color(0xFF252525)
val LifeDarkPrimary = Color(0xFFFAFAFA) // --primary
val LifeDarkPrimaryForeground = Color(0xFF3D3D3D) // --primary-foreground oklch(0.205)
val LifeDarkSecondary = Color(0xFF404040) // --secondary oklch(0.269)
val LifeDarkMuted = Color(0xFF404040)
val LifeDarkMutedForeground = Color(0xFFB0B0B0) // oklch(0.708)
val LifeDarkAccent = Color(0xFF404040)
val LifeDarkDestructive = Color(0xFF9B2335) // destructive dark approx
val LifeDarkBorder = Color(0xFF404040)

// Legacy names (retained for any external references; prefer Life* tokens)
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)
val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
