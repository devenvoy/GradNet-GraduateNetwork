package com.sdjic.gradnet.presentation.composables.images

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sdjic.gradnet.presentation.composables.text.SText

@Composable
fun LongBackButton(
    modifier: Modifier = Modifier,
    iconColor: Color = MaterialTheme.colorScheme.primary,
    isEnable: Boolean = true,
    onBackPressed: () -> Unit
) {
    Row(
        modifier = modifier.clickable(enabled = isEnable, onClick = onBackPressed),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
            contentDescription = null,
            tint = iconColor
        )
        SText(text = "Back", textColor = iconColor)
    }
}