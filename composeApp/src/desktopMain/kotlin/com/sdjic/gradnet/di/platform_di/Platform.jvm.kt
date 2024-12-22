package com.sdjic.gradnet

import com.sdjic.gradnet.di.platform_di.Platform

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()