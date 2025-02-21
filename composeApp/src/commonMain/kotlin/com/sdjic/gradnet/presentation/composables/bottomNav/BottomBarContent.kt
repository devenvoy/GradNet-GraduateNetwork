package com.sdjic.gradnet.presentation.composables.bottomNav

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdjic.gradnet.presentation.helper.MyTab
import org.jetbrains.compose.resources.painterResource

@Composable
private fun BottomBarContent(
    items: List<MyTab>,
    selectedIndex: Int,
    onItemSelected: (MyTab) -> Unit
) {
    CompositionLocalProvider(
        LocalTextStyle provides LocalTextStyle.current.copy(
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
        ),
        LocalContentColor provides Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
        ) {
            for ((index, item) in items.withIndex()) {
                val alpha by animateFloatAsState(
                    targetValue = if (selectedIndex == index) 1f else .35f,
                    label = "alpha"
                )
                val scale by animateFloatAsState(
                    targetValue = if (selectedIndex == index) 1f else .98f,
                    visibilityThreshold = .000001f,
                    animationSpec = spring(
                        stiffness = Spring.StiffnessLow,
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                    ),
                    label = "scale"
                )
                Column(
                    modifier = Modifier
                        .scale(scale)
                        .alpha(alpha)
                        .fillMaxHeight()
                        .weight(1f)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                onItemSelected(item)
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(painter = painterResource(item.tabOption.unselectedIcon), contentDescription = "tab ${item.tabOption.title}")
                    Text(text = item.tabOption.title)
                }
            }
        }
    }
}

@Composable
private fun GlowingBackground(
    items: List<MyTab>,
    selectedIndex: Int
) {
    val animatedSelectedIndex by animateFloatAsState(
        targetValue = selectedIndex.toFloat(),
        label = "animatedSelectedIndex",
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioLowBouncy,
        )
    )

    val animatedColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.secondaryContainer,
        label = "animatedColor",
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
        )
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clip(CircleShape)
            .blur(50.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
    ) {
        val tabWidth = size.width / items.size
        drawCircle(
            color = animatedColor.copy(alpha = .6f),
            radius = size.height / 2,
            center = Offset(
                (tabWidth * animatedSelectedIndex) + tabWidth / 2,
                size.height / 2
            )
        )
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clip(CircleShape)
    ) {
        val path = Path().apply {
            addRoundRect(RoundRect(size.toRect(), CornerRadius(size.height)))
        }
        val length = PathMeasure().apply { setPath(path, false) }.length

        val tabWidth = size.width / items.size
        drawPath(
            path,
            brush = Brush.horizontalGradient(
                colors = listOf(
                    animatedColor.copy(alpha = 0f),
                    animatedColor.copy(alpha = 1f),
                    animatedColor.copy(alpha = 1f),
                    animatedColor.copy(alpha = 0f),
                ),
                startX = tabWidth * animatedSelectedIndex,
                endX = tabWidth * (animatedSelectedIndex + 1),
            ),
            style = Stroke(
                width = 6f,
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(length / 2, length)
                )
            )
        )
    }
}
