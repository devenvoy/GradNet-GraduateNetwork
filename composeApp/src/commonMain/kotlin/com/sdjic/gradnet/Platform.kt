package com.sdjic.gradnet

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform