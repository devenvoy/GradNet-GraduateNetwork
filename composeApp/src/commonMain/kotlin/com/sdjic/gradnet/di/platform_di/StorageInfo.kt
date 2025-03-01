package com.sdjic.gradnet.di.platform_di

data class StorageInfo(
    val totalStorage: Float,// in GB
    val freeStorage: Float,// in GB
    val usedByOtherApps: Float,// in GB
    val usedByApp: Float, // in MB
    val usedByCache: Float // in MB
)

expect fun getStorageInfo(): StorageInfo

fun roundTwoDecimals(value: Float): Float {
    return (value * 100).toInt() / 100f
}