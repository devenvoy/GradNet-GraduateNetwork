package com.sdjic.gradnet.data.local.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.sdjic.gradnet.data.local.entity.Test
import com.sdjic.gradnet.di.platform_di.AppDatabaseConstructor

@Database(entities = [Test::class], version = 1, exportSchema = false)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class GradNetDB :RoomDatabase(){
    abstract val testDao: TestDao
}