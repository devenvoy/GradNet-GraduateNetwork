package com.sdjic.gradnet.presentation.composables.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun FancyIndicator(color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier
            .padding(5.dp)
            .fillMaxSize()
            .zIndex(-1f)
            .background(color, RoundedCornerShape(5.dp))
    )
}