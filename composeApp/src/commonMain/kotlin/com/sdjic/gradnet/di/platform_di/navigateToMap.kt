package com.sdjic.gradnet.di.platform_di

import com.sdjic.gradnet.domain.location.DeviceLocation

expect fun navigateToMap(
    context: PlatformContext,
    deviceLocation: DeviceLocation?,
    destinationName: String
)