package com.sdjic.gradnet.presentation.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sdjic.gradnet.presentation.composables.text.SText
import network.chaintech.sdpcomposemultiplatform.sdp
import network.chaintech.sdpcomposemultiplatform.ssp

@Composable
fun SectionTitle(
    icon: ImageVector,
    title: String,
) {
    Row(
        modifier = Modifier.padding(start = 6.sdp, top = 10.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(4.sdp))
        SText(
            text = title,
            fontSize = 14.ssp,
            fontWeight = FontWeight.W600,
            textColor = MaterialTheme.colorScheme.onBackground,
        )
    }
}


@Composable
fun SectionTitle(
    icon: Painter?,
    title: String,
) {
    Row(
        modifier = Modifier.padding(start = 6.sdp, top = 10.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = icon,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(4.sdp))
        }
        SText(
            text = title,
            fontSize = 14.ssp,
            fontWeight = FontWeight.W600,
            textColor = MaterialTheme.colorScheme.onBackground,
        )
    }
}