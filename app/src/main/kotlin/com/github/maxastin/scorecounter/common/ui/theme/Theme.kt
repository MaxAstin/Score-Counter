package com.github.maxastin.scorecounter.common.ui.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

private val DarkColorScheme = ColorScheme(
    important = Red,
    primary = Blue,
    icon = White,
    iconVariant = Gray200,
    surface = Black200,
    surfaceVariant = Gray400,
    selectedSurface = Black100,
    onSurface = White,
    onSurfaceVariant = Gray200,
    background = White,
    onBackground = Black200,
    border = Gray300,
    borderVariant = Gray100,
    instagram = InstagramColors(
        logo1 = BrightPurple,
        logo2 = Red,
        logo3 = Amber,
        accent = Pink,
    ),
)

private val LightColorScheme = ColorScheme(
    primary = Blue,
    important = Red,
    icon = White,
    iconVariant = Gray200,
    surface = Black200,
    surfaceVariant = Gray400,
    selectedSurface = Black100,
    onSurface = White,
    onSurfaceVariant = Gray200,
    background = White,
    onBackground = Black200,
    border = Gray300,
    borderVariant = Gray100,
    instagram = InstagramColors(
        logo1 = BrightPurple,
        logo2 = Red,
        logo3 = Amber,
        accent = Pink,
    ),
)

val LocalFakeLiveColors = staticCompositionLocalOf { LightColorScheme }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScoreCounterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val rememberedColors = remember {
        colorScheme.copy()
    }

    CompositionLocalProvider(
        LocalOverscrollConfiguration provides null,
        LocalFakeLiveColors provides rememberedColors,
        LocalScoreCounterTypography provides ScoreCounterTypography(),
        content = content
    )
}

object ScoreCounterTheme {
    val colors: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalFakeLiveColors.current
    val typography: ScoreCounterTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalScoreCounterTypography.current
}