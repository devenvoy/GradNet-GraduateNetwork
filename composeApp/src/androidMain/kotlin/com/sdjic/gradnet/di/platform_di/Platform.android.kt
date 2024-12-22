package com.sdjic.gradnet

import android.os.Build
import com.sdjic.gradnet.di.platform_di.Platform

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()