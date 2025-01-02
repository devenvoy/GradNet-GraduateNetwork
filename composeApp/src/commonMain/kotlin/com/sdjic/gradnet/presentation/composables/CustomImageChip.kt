package com.sdjic.gradnet.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun CustomImageChip(
    text: String,
    selected: Boolean,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = when {
            selected -> MaterialTheme.colorScheme.primaryContainer
            else -> Color.Transparent
        },
        contentColor = when {
            selected -> MaterialTheme.colorScheme.onPrimary
            else -> Color.LightGray
        },
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = when {
                selected -> MaterialTheme.colorScheme.primaryContainer
                else -> Color.LightGray
            }
        ),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            SText(
                text = text,
                modifier = Modifier.padding(8.sdp),
                textColor = MaterialTheme.colorScheme.onSecondary
            )
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Done icon",
                modifier = Modifier.padding(end = 8.sdp).clickable(onClick = { onCancelClick() })
            )
        }
    }
}