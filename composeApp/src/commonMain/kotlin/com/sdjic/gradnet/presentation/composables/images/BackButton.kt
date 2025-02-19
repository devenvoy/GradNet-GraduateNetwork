package com.sdjic.gradnet.presentation.composables.images

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ArrowLeft

@Composable
    fun BackButton(onBackPressed: () -> Unit) {
        IconButton(onClick = onBackPressed) {
            Icon(
                imageVector = FontAwesomeIcons.Solid.ArrowLeft,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }