package com.sdjic.gradnet.presentation.helper

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


expect fun Modifier.coloredShadow(
    color: Color,
    alpha: Float = 0.2f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 20.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
): Modifier

fun colorFromHex(hex: String): Color {
    val cleanHex = hex.removePrefix("#")

    // Parse color components
    val colorLong = cleanHex.toLong(16)
    return when (cleanHex.length) {
        6 -> Color(
            red = ((colorLong shr 16) and 0xFF) / 255f,
            green = ((colorLong shr 8) and 0xFF) / 255f,
            blue = (colorLong and 0xFF) / 255f,
            alpha = 1f
        )
        8 -> Color(
            alpha = ((colorLong shr 24) and 0xFF) / 255f,
            red = ((colorLong shr 16) and 0xFF) / 255f,
            green = ((colorLong shr 8) and 0xFF) / 255f,
            blue = (colorLong and 0xFF) / 255f
        )
        else -> throw IllegalArgumentException("Invalid hex color format: $hex")
    }
}