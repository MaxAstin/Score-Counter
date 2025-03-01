package com.github.maxastin.scorecounter.common.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

val White = Color(0xFFFFFFFF)
val Black200 = Color(0xFF000000)
val Black100 = Color(0xFF1A1A1A)
val Gray400 = Color(0xFF262626)
val Gray300 = Color(0xFF6D6D6D)
val Gray200 = Color(0xFF888888)
val Gray100 = Color(0xFFDBDBDB)
val Blue = Color(0xFF0195F7)
val Red = Color(0xFFDD6962)
val Green = Color(0xFF2BD587)

@Stable
class ColorScheme(
    primary: Color,
    negative: Color,
    positive: Color,
    icon: Color,
    iconVariant: Color,
    surface: Color,
    surfaceVariant: Color,
    selectedSurface: Color,
    onSurface: Color,
    onSurfaceVariant: Color,
    background: Color,
    onBackground: Color,
    border: Color,
    borderVariant: Color
) {

    var primary by mutableStateOf(primary)
        internal set

    var negative by mutableStateOf(negative)
        internal set

    var positive by mutableStateOf(positive)
        internal set

    var icon by mutableStateOf(icon)
        internal set

    var iconVariant by mutableStateOf(iconVariant)
        internal set

    var surface by mutableStateOf(surface)
        internal set

    var surfaceVariant by mutableStateOf(surfaceVariant)
        internal set

    var selectedSurface by mutableStateOf(selectedSurface)
        internal set

    var onSurface by mutableStateOf(onSurface)
        internal set

    var onSurfaceVariant by mutableStateOf(onSurfaceVariant)
        internal set

    var background by mutableStateOf(background)
        internal set

    var onBackground by mutableStateOf(onBackground)
        internal set

    var border by mutableStateOf(border)
        internal set

    var borderVariant by mutableStateOf(borderVariant)
        internal set

    fun copy(
        primary: Color = this.primary,
        negative: Color = this.negative,
        positive: Color = this.positive,
        icon: Color = this.icon,
        iconVariant: Color = this.iconVariant,
        surface: Color = this.surface,
        surfaceVariant: Color = this.surfaceVariant,
        selectedSurface: Color = this.selectedSurface,
        onSurface: Color = this.onSurface,
        onSurfaceVariant: Color = this.onSurfaceVariant,
        background: Color = this.background,
        onBackground: Color = this.onBackground,
        border: Color = this.border,
        borderVariant: Color = this.borderVariant
    ): ColorScheme = ColorScheme(
        primary = primary,
        negative = negative,
        positive = positive,
        icon = icon,
        iconVariant = iconVariant,
        surface = surface,
        surfaceVariant = surfaceVariant,
        selectedSurface = selectedSurface,
        onSurface = onSurface,
        onSurfaceVariant = onSurfaceVariant,
        background = background,
        onBackground = onBackground,
        border = border,
        borderVariant = borderVariant
    )
}