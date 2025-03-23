package com.sdjic.gradnet.di.platform_di

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun WebView(
    modifier: Modifier,
    link: String,
)