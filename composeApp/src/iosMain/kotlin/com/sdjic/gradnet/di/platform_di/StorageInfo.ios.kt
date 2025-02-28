package com.sdjic.gradnet.di.platform_di

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.*

@OptIn(ExperimentalForeignApi::class)
actual fun getStorageInfo(): StorageInfo {
    val fileManager = NSFileManager.defaultManager
    val homeDir = NSHomeDirectory()
    val attributes = fileManager.attributesOfFileSystemForPath(homeDir, null)

    val totalSpace = (attributes?.get(NSFileSystemSize) as? NSNumber)?.doubleValue ?: 0.0
    val freeSpace = (attributes?.get(NSFileSystemFreeSize) as? NSNumber)?.doubleValue ?: 0.0

    // Convert to GB with float precision
    val totalGB = (totalSpace / (1024 * 1024 * 1024)).toFloat()
    val freeGB = (freeSpace / (1024 * 1024 * 1024)).toFloat()

    // App storage
    val appFilesDir = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true).firstOrNull() as? String ?: ""
    val appCacheDir = NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, true).firstOrNull() as? String ?: ""

    val usedByApp = getFolderSize(appFilesDir) / (1024f * 1024f) // MB with float
    val usedByCache = getFolderSize(appCacheDir) / (1024f * 1024f) // MB with float

    return StorageInfo(totalGB, freeGB, totalGB - freeGB, usedByApp, usedByCache, 0f)
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
    return size.toFloat() // Convert to Float for precision
}
