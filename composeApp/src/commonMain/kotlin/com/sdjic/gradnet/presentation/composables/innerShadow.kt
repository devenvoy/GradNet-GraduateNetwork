package com.sdjic.gradnet.presentation.composables

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Adds an inner shadow effect to the content.
 *
 * @param shape The shape of the shadow.
 * @param color The color of the shadow.
 * @param blur The blur radius of the shadow.
 * @param offsetY The shadow offset along the Y-axis.
 * @param offsetX The shadow offset along the X-axis.
 * @param spread The amount to expand the shadow beyond its size.
 *
 * @return A modified Modifier with the inner shadow effect applied.
 */

// Define a common function for the innerShadow modifier
fun Modifier.innerShadow(
    shape: Shape,
    color: Color = Color.Black,
    blur: Dp = 4.dp,
    offsetY: Dp = 2.dp,
    offsetX: Dp = 2.dp,
    spread: Dp = 0.dp
): Modifier {
    return this.drawWithContent {
        // Draw the content first (this will be the element inside the shadow)
        drawContent()

        // Abstracted shadow drawing logic
        drawIntoCanvas { canvas ->
            val shadowSize = androidx.compose.ui.geometry.Size(
                size.width + spread.toPx(),
                size.height + spread.toPx()
            )
            val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)

            val paint = Paint().apply {
                this.color = color
            }

            // Save the canvas state before drawing the shadow
            canvas.saveLayer(size.toRect(), paint)
            canvas.drawOutline(shadowOutline, paint)

            // Apply shadow offset (if any)
            canvas.translate(offsetX.toPx(), offsetY.toPx())
            canvas.drawOutline(shadowOutline, paint)

            // Restore canvas state after drawing the shadow
            canvas.restore()
        }
    }
}
