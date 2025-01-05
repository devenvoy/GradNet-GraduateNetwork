package com.sdjic.gradnet.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil3.PlatformContext
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun BackgroundImage(
    modifier: Modifier = Modifier,
    context: PlatformContext,
    data: Any? = null,
    imageBitmap: ImageBitmap? = null,
    backgroundColor: Color = Color.LightGray,
    height: Dp = 120.sdp
) {
    Box(
        modifier = modifier.fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(4.sdp))
            .background(backgroundColor, RoundedCornerShape(4.sdp)),
        contentAlignment = Alignment.Center
    ) {

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data)
                .crossfade(true)
                .crossfade(300)
                .build(),
            contentDescription = "background image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxSize()
        )
        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap,
                contentDescription = "background image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
