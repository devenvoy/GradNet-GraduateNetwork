package com.sdjic.gradnet.di.platform_di

import android.content.Intent
import androidx.compose.runtime.NoLiveLiterals
import androidx.core.net.toUri
import com.sdjic.gradnet.domain.location.DeviceLocation

@NoLiveLiterals
actual fun navigateToMap(
    context: PlatformContext,
    deviceLocation: DeviceLocation?,
    destinationName: String
) {
    val destinationUri = "google.navigation:q=${deviceLocation?.latitude},${deviceLocation?.longitude}"

    val intent = Intent(Intent.ACTION_VIEW, destinationUri.toUri())

    if (intent.resolveActivity(context.androidContext.packageManager) != null) {
        context.androidContext.startActivity(intent)
    }
}