
package com.sdjic.gradnet.presentation.composables.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun ContactIconButton(
    icon: Painter,
    contentDescription: String? = null,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(42.sdp)
            .padding(4.dp)
            .border(1.dp, Color.Gray, CircleShape)
            .background(MaterialTheme.colorScheme.surface, CircleShape)
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(24.sdp)
        )
    }
}