package com.sdjic.gradnet.di.platform_di

import androidx.room.RoomDatabase
import com.sdjic.gradnet.data.local.GradNetDB

expect fun getDatabaseBuilder(): PlatformDatabaseBuilder

interface PlatformDatabaseBuilder{
    fun build(): RoomDatabase.Builder<GradNetDB>
}