package com.sdjic.gradnet.presentation.helper
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp

actual fun Modifier.coloredShadow(
    color: Color,
    alpha: Float,
    borderRadius: Dp,
    shadowRadius: Dp,
    offsetY: Dp,
    offsetX: Dp
): Modifier = this.drawBehind {
    val paint = Paint().apply {
        this.color = color.copy(alpha = alpha)
    }

    // Simulate shadow using a semi-transparent rounded rectangle
    drawIntoCanvas { canvas ->
        canvas.drawRoundRect(
            left = offsetX.toPx(),
            top = offsetY.toPx(),
            right = this.size.width + offsetX.toPx(),
            bottom = this.size.height + offsetY.toPx(),
            radiusX = borderRadius.toPx(),
            radiusY = borderRadius.toPx(),
            paint = paint
        )
    }
}
