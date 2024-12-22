package com.sdjic.gradnet

import com.sdjic.gradnet.di.platform_di.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}