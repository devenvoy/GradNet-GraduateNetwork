package com.sdjic.gradnet.presentation.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import compose.icons.FeatherIcons
import compose.icons.feathericons.Plus

@Composable
fun PlusIconButton(onClick: () -> Unit) {
    IconButton(
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        ),
        onClick = onClick
    ) {
        Icon(
            imageVector = FeatherIcons.Plus,
            contentDescription = "Done icon",
        )
    }
}