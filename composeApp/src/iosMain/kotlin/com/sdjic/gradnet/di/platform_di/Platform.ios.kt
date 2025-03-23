package com.sdjic.gradnet.di.platform_di

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import platform.UIKit.UIActivityViewController
import androidx.compose.ui.unit.dp
import platform.UIKit.UIDevice
import platform.UIKit.UIScreen
import androidx.compose.ui.graphics.asSkiaBitmap
import com.sdjic.gradnet.Holder
import org.jetbrains.skia.EncodedImageFormat
import org.jetbrains.skia.Image

class IOSPlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getScreenWidth(): Dp = LocalWindowInfo.current.containerSize.width.pxToPoint().dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getScreenHeight(): Dp = LocalWindowInfo.current.containerSize.height.pxToPoint().dp

fun Int.pxToPoint(): Double = this.toDouble() / UIScreen.mainScreen.scale

actual fun ImageBitmap.toByteArray(): ByteArray {
    val skiaImage = Image.makeFromBitmap(this.asSkiaBitmap())
    return skiaImage.encodeToData(EncodedImageFormat.JPEG)?.bytes ?: ByteArray(0)
}

actual fun share(context: PlatformContext, text: String) {
    val controller = UIActivityViewController(listOf(text), null)

    Holder.viewController?.presentViewController(controller, true, null)
}