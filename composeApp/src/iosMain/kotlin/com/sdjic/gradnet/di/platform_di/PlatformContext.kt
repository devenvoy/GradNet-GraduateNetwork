package com.sdjic.gradnet.di.platform_di

import androidx.compose.runtime.Composable

actual class PlatformContext

@Composable
actual fun getPlatformContext(): PlatformContext = PlatformContext()