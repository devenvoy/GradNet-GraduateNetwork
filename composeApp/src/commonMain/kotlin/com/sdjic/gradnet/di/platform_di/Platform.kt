package com.sdjic.gradnet.di.platform_di

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

@Composable
expect fun getScreenWidth(): Dp

@Composable
expect fun getScreenHeight(): Dp

expect fun ImageBitmap.toByteArray(): ByteArray


expect fun share (context: PlatformContext, text: String)