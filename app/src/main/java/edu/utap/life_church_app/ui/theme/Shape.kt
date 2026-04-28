package edu.utap.life_church_app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Corner radii from `theme.css`: `--radius: 0.625rem` (10px at 16px root), sm/md/lg/xl.
 */
val LifeRadiusSm = 6.dp
val LifeRadiusMd = 8.dp
val LifeRadiusLg = 10.dp
val LifeRadiusXl = 14.dp

val LifeShapes = Shapes(
    extraSmall = RoundedCornerShape(LifeRadiusSm),
    small = RoundedCornerShape(LifeRadiusMd),
    medium = RoundedCornerShape(LifeRadiusLg),
    large = RoundedCornerShape(LifeRadiusXl),
    extraLarge = RoundedCornerShape(LifeRadiusXl)
)
