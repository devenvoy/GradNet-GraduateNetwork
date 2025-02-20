package com.sdjic.gradnet.presentation.composables.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
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
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun CircularProfileImage(
    modifier: Modifier = Modifier,
    context: PlatformContext,
    data: Any?,
    imageBitmap: ImageBitmap? = null,
    imageSize: Dp = 70.sdp,
    borderColor: Color = MaterialTheme.colorScheme.surface,
    borderWidth: Dp = 2.sdp,
    backgroundColor: Color = Color.LightGray
) {
    Box(
        modifier = modifier
            .size(imageSize)
            .background(backgroundColor, shape = CircleShape)
            .border(borderWidth, borderColor, CircleShape)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(data)
                .crossfade(true)
                .crossfade(300)
                .diskCachePolicy(CachePolicy.ENABLED)
                .diskCacheKey("image_${data.hashCode()}")
                .build(),
            contentDescription = "user profile image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        imageBitmap?.let {
            Image(
                bitmap = imageBitmap,
                contentDescription = "user profile image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
        }
    }
}
