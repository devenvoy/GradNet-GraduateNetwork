package com.sdjic.gradnet.di.platform_di

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
