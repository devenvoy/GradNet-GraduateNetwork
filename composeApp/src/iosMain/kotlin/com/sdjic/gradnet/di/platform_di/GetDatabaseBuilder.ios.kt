package com.sdjic.gradnet.di.platform_di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.sdjic.gradnet.data.local.GradNetDB
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual fun getDatabaseBuilder(): PlatformDatabaseBuilder = IosDatabaseBuilder()

class IosDatabaseBuilder:PlatformDatabaseBuilder{
    override fun build(): RoomDatabase.Builder<GradNetDB> {
        val dbFile = documentDirectory() + "/gradNetDb.db"
        return Room.databaseBuilder<GradNetDB>(
            name = dbFile,
        )
            .setDriver(BundledSQLiteDriver())
    }
    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory?.path)
    }
}