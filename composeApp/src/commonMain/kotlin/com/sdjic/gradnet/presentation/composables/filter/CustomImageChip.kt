package com.sdjic.gradnet.presentation.composables.filter

import androidx.compose.foundation.BorderStroke
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
import com.sdjic.gradnet.presentation.composables.text.SText
import network.chaintech.kmp_date_time_picker.utils.noRippleEffect
import network.chaintech.sdpcomposemultiplatform.sdp

@Composable
fun CustomImageChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit = {},
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
    showEndIcon: Boolean = true
) {
    Surface(
        color = when {
            selected -> MaterialTheme.colorScheme.primaryContainer
            else -> Color.Transparent
        },
        contentColor = when {
            selected -> MaterialTheme.colorScheme.onPrimaryContainer
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
        onClick = { onClick() },
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            SText(
                text = text,
                modifier = Modifier.padding(8.sdp),
                textColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
            if (selected && showEndIcon) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "close icon",
                    modifier = Modifier.padding(end = 8.sdp).noRippleEffect(onCloseClick)
                )
            }
        }
    }
}