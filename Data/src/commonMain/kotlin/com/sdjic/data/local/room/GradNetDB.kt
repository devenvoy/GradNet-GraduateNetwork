package com.sdjic.data.local.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.sdjic.data.local.entity.Test

@Database(entities = [Test::class], version = 1, exportSchema = false)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class GradNetDB :RoomDatabase(){
    abstract val testDao: TestDao
}