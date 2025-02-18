package com.sdjic.commons.composables.filter


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp


@Composable
fun TopicChip(
    modifier: Modifier = Modifier,
    topic: String,
    onCancel: () -> Unit = {},
) {

    FilterChip(
        modifier = modifier,
        selected = true,
        onClick = {},
        shape = MaterialTheme.shapes.large,
        colors = FilterChipDefaults.filterChipColors()
            .copy(selectedContainerColor = Color(0xFFEEF0FF)),
        leadingIcon = {
            Icon(
                Icons.Outlined.Info,
                modifier = Modifier.size(16.dp),
                contentDescription = null,
                tint = Color.DarkGray,
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Outlined.Close,
                modifier = Modifier.size(16.dp).clickable { onCancel() },
                contentDescription = "Cancel",
                tint = Color.DarkGray,
            )
        },
        label = {
            Text(
                text = topic,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF40434F),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    )
}