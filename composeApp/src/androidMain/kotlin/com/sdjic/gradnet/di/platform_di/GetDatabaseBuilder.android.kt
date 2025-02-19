package com.sdjic.gradnet.di.platform_di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.sdjic.data.local.room.GradNetDB
import com.sdjic.gradnet.GradNetApp

actual fun getDatabaseBuilder(): PlatformDatabaseBuilder = AndroidDatabaseBuilder(GradNetApp.AppContext)

class AndroidDatabaseBuilder(private val context: Context):PlatformDatabaseBuilder{
    override fun build(): RoomDatabase.Builder<GradNetDB> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath("translate.db")

        return Room.databaseBuilder<GradNetDB>(
            context = appContext,
            name = dbFile.absolutePath
        ).setDriver(BundledSQLiteDriver())
    }
}