package com.sdjic.gradnet.di.platform_di

import android.annotation.SuppressLint
import android.content.Context
import com.sdjic.gradnet.domain.location.LocationRepository
import com.google.android.gms.location.LocationServices
import com.sdjic.gradnet.domain.location.DeviceLocation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class AndroidLocationRepository(
    private val context: Context,
) : LocationRepository {

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): DeviceLocation = suspendCoroutine { continuation ->
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location == null) return@addOnSuccessListener
                continuation.resume(
                    DeviceLocation(
                        latitude = location.latitude,
                        longitude = location.longitude,
                    ),
                )
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }
}