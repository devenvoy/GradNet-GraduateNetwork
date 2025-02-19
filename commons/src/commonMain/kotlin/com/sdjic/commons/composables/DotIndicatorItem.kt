package com.sdjic.commons.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun DotIndicatorItem(
        isSelected: Boolean,
        spacer: Int,
        size: Int,
        selectedLength: Int,
        selectedColor: Color,
        unselectedColor: Color
    ) {
        val color: Color by animateColorAsState(
            targetValue = if (isSelected) selectedColor else unselectedColor,
            animationSpec = tween(durationMillis = 500)
        )
        val width: Dp by animateDpAsState(
            targetValue = if (isSelected) selectedLength.sdp else size.sdp,
            animationSpec = tween(durationMillis = 500)
        )

        Box(
            modifier = Modifier
                .padding(horizontal = spacer.sdp / 2)
                .size(width = width, height = size.sdp)
                .clip(CircleShape)
                .background(color)
        )
    }