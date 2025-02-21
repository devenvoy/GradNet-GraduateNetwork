package com.sdjic.gradnet.presentation.composables.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun RoundedCornerImage(
    modifier: Modifier = Modifier,
    imageBitmap: ImageBitmap,
    imageSize: Dp = 70.sdp,
    shape: Shape = CardDefaults.shape
) {
    Card(
        modifier = modifier.size(imageSize),
        shape = shape
    ) {
        Image(
            bitmap = imageBitmap,
            contentDescription = "user profile image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Composable
fun RoundedCornerImage(
    modifier: Modifier = Modifier,
    data: Any?,
    imageSize: Dp = 70.sdp,
    shape: Shape = CardDefaults.shape
) {
    Card(
        modifier = modifier.size(imageSize),
        shape = shape
    ) {
        AsyncImage(
            model = data,
            contentDescription = "user profile image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}