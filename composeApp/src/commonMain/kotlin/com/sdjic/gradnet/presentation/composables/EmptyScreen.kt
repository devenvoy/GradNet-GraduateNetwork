package com.sdjic.gradnet.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sdjic.gradnet.presentation.composables.text.Title

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    title: String = "Empty Screen"
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Title(title)
    }
}