package com.github.maxastin.scorecounter.common.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.github.maxastin.scorecounter.R

internal val LocalScoreCounterTypography = staticCompositionLocalOf { ScoreCounterTypography() }

@Immutable
data class ScoreCounterTypography(
    val titleSmall: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    val titleMedium: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontWeight = FontWeight.W500,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    val titleLarge: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight.W400,
        fontSize = 24.sp,
        lineHeight = 30.sp
    ),
    val bodySmall: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 18.sp
    ),
    val bodyMedium: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    val bodyLarge: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
)

val TextStyle.bold: TextStyle
    get() {
        return copy(fontWeight = FontWeight.Bold)
    }