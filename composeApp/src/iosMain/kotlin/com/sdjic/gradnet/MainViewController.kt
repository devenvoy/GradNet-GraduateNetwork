package com.sdjic.gradnet

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
   val viewController = ComposeUIViewController { App() }
    Holder.viewController = viewController
    return viewController
}

internal object Holder {
    var viewController: UIViewController? = null
}