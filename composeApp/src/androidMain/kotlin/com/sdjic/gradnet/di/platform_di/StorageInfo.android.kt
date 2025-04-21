package com.sdjic.gradnet.di.platform_di

import android.app.usage.StorageStats
import android.app.usage.StorageStatsManager
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StatFs
import com.sdjic.gradnet.GradNetApp
import java.io.File

actual fun getStorageInfo(): StorageInfo {
    val context = GradNetApp.AppContext
    val totalStorage = getTotalStorage()
    val freeStorage = getFreeStorage()
    val usedByApp = getAppStorageUsage(context)
    val usedByCache = getCacheSize(context)
    val usedByOtherApps = totalStorage - freeStorage - (usedByApp / 1024)

    return StorageInfo(
        totalStorage = totalStorage,
        freeStorage = freeStorage,
        usedByOtherApps = usedByOtherApps,
        usedByApp = usedByApp,
        usedByCache = usedByCache
    )
}

/** 📌 Get Total Storage of the Device */
fun getTotalStorage(): Float {
    val stat = StatFs(Environment.getDataDirectory().path)
    return (stat.totalBytes / (1024.0 * 1024 * 1024)).toFloat() // Convert to GB
}

/** 📌 Get Free Storage Available */
fun getFreeStorage(): Float {
    val stat = StatFs(Environment.getDataDirectory().path)
    return (stat.availableBytes / (1024.0 * 1024 * 1024)).toFloat() // Convert to GB
}

/** 📌 Get Storage Used by Current App (IN MB) */
fun getAppStorageUsage(context: Context): Float {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        try {
            val storageStatsManager =
                context.getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager
            val appInfo = context.applicationInfo
            val storageStats: StorageStats =
                storageStatsManager.queryStatsForUid(appInfo.storageUuid, appInfo.uid)

            val appSize = storageStats.appBytes + storageStats.dataBytes + storageStats.cacheBytes
            return (appSize / (1024.0 * 1024.0)).toFloat() // Convert to MB
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return 0F
}

/** 📌 Get Cache Size Used by the App (IN MB) */
fun getCacheSize(context: Context): Float {
    return (getFolderSize(context.cacheDir) / (1024.0 * 1024.0)).toFloat() // Convert to MB
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

actual fun clearCache() {
    val context: Context = GradNetApp.AppContext
    try {
        context.cacheDir?.deleteRecursively()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}