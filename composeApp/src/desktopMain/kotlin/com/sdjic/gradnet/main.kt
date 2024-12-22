package com.sdjic.gradnet

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "GradNet - Graduate Network",
    ) {
        App()
    }
}