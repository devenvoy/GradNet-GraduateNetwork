package com.sdjic.gradnet.di.platform_di

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSFileSize
import platform.Foundation.NSFileSystemFreeSize
import platform.Foundation.NSFileSystemSize
import platform.Foundation.NSHomeDirectory
import platform.Foundation.NSNumber
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask


actual fun getStorageInfo(): StorageInfo {
    val totalStorage = getTotalStorage()
    val freeStorage = getFreeStorage()
    val usedByApp = getAppStorageUsage()
    val usedByCache = getCacheSize()
    val usedByOtherApps = totalStorage - freeStorage - usedByApp

    return StorageInfo(
        totalStorage = totalStorage,
        freeStorage = freeStorage,
        usedByOtherApps = usedByOtherApps,
        usedByApp = usedByApp,
        usedByCache = usedByCache
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun getTotalStorage(): Float {
    val systemAttributes = NSFileManager.defaultManager.attributesOfFileSystemForPath(NSHomeDirectory(), null)
    val totalSize = systemAttributes?.get(NSFileSystemSize) as? NSNumber
    return totalSize?.floatValue?.div(1024 * 1024 * 1024) ?: 0f
}

@OptIn(ExperimentalForeignApi::class)
private fun getFreeStorage(): Float {
    val systemAttributes = NSFileManager.defaultManager.attributesOfFileSystemForPath(NSHomeDirectory(), null)
    val freeSize = systemAttributes?.get(NSFileSystemFreeSize) as? NSNumber
    return freeSize?.doubleValue?.div(1024.0 * 1024.0 * 1024.0)?.toFloat() ?: 0f
}

private fun getAppStorageUsage(): Float {
    return getFolderSize(NSHomeDirectory()) // Returns in MB
}

private fun getCacheSize(): Float {
    val cacheDir = NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, true).firstOrNull() as? String ?: ""
    return cacheDir?.let { getFolderSize(it) } ?: 0f // Returns in MB
}


@OptIn(ExperimentalForeignApi::class)
fun getFolderSize(path: String): Float {
    val fileManager = NSFileManager.defaultManager
    val files = fileManager.subpathsAtPath(path) ?: return 0f

    var size = 0L
    files.forEach {
        val filePath = "$path/$it"
        val attributes = fileManager.attributesOfItemAtPath(filePath, null)
        size += (attributes?.get(NSFileSize) as? NSNumber)?.longLongValue ?: 0L
    }
    return size.toFloat() / (1024 * 1024)
}
