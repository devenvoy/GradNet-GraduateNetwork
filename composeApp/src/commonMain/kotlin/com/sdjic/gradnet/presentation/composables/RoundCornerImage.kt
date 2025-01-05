package com.sdjic.gradnet.presentation.composables

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil3.PlatformContext
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun RoundedCornerImage(
    modifier: Modifier = Modifier,
    context:PlatformContext,
    data: Any?,
    imageSize: Dp = 70.sdp,
    shape: Shape = CardDefaults.shape
) {
    Card(
        modifier = modifier
            .size(imageSize),
        shape = shape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.sdp,
            pressedElevation = 10.sdp,
            focusedElevation = 10.sdp,
            hoveredElevation = 10.sdp,
            draggedElevation = 10.sdp,
            disabledElevation = 10.sdp
        ),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data)
                .crossfade(true)
                .crossfade(300)
                .build(),
            contentDescription = "round image",
            contentScale = ContentScale.Crop,
        )
    }
}