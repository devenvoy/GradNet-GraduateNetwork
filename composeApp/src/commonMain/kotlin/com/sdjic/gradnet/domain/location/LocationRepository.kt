package com.sdjic.gradnet.domain.location

interface LocationRepository {
    suspend fun getCurrentLocation(): DeviceLocation
}
