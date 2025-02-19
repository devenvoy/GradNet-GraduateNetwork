package com.sdjic.gradnet.di.platform_di

import androidx.room.RoomDatabase
import com.sdjic.data.local.room.GradNetDB

expect fun getDatabaseBuilder(): PlatformDatabaseBuilder

interface PlatformDatabaseBuilder{
    fun build(): RoomDatabase.Builder<GradNetDB>
}