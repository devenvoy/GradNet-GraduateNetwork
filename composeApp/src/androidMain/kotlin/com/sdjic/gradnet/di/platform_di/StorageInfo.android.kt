package com.sdjic.gradnet.di.platform_di

import android.content.Context
import android.os.Environment
import android.os.StatFs
import com.sdjic.gradnet.GradNetApp
import java.io.File

actual fun getStorageInfo(): StorageInfo {
    val context =GradNetApp.AppContext
    val statFs = StatFs(Environment.getExternalStorageDirectory().absolutePath)
    val totalSpace = statFs.totalBytes.toFloat() / (1024 * 1024 * 1024) // Convert to GB
    val freeSpace = statFs.availableBytes.toFloat() / (1024 * 1024 * 1024) // Convert to GB
    val usedSpace = totalSpace - freeSpace // Total used space in GB

    // App directories
    val appFilesDir = context.filesDir
    val appCacheDir = context.cacheDir
    val appDownloadDir = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "raw_downloads")

    // Get precise storage sizes
    val usedByApp = getFolderSize(appFilesDir) / (1024f * 1024f) // MB with float precision
    val usedByCache = getFolderSize(appCacheDir) / (1024f * 1024f) // MB with float precision
    val usedByDownloadDir = getFolderSize(appDownloadDir) / (1024f * 1024f) // MB with float precision

    // Used by other apps (Total used - This app usage)
    val usedByOtherApps = usedSpace - (usedByApp / 1024f) // Convert MB to GB with float precision

    return StorageInfo(totalSpace, freeSpace, usedByOtherApps, usedByApp, usedByCache, usedByDownloadDir)
}

// Function to calculate folder size with precision
fun getFolderSize(file: File): Float {
    var size = 0L
    if (file.exists() && file.isDirectory) {
        file.listFiles()?.forEach { child ->
            size += if (child.isDirectory) getFolderSize(child).toLong() else child.length()
        }
    }
    return size.toFloat() // Return size as Float for precision
}