package com.sdjic.gradnet.data.local.room

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sdjic.gradnet.data.local.entity.EducationTable
import com.sdjic.gradnet.data.local.entity.ExperienceTable
import com.sdjic.gradnet.data.local.entity.PostRemoteKeys
import com.sdjic.gradnet.data.local.entity.Test
import com.sdjic.gradnet.data.local.entity.UrlTable
import com.sdjic.gradnet.data.local.room.dao.PostDao
import com.sdjic.gradnet.data.local.room.dao.PostRemoteKeysDao
import com.sdjic.gradnet.data.local.room.dao.TestDao
import com.sdjic.gradnet.data.local.room.dao.UserDao
import com.sdjic.gradnet.data.network.entity.dto.Converters
import com.sdjic.gradnet.data.network.entity.dto.PostTable
import com.sdjic.gradnet.di.platform_di.AppDatabaseConstructor

@Database(
    entities = [
        Test::class,
        ExperienceTable::class,
        EducationTable::class,
        UrlTable::class,
        PostTable::class,
        PostRemoteKeys::class
    ], version = 1, exportSchema = false
)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(Converters::class)
abstract class GradNetDB : RoomDatabase() {
    abstract val testDao: TestDao
    abstract val userDao: UserDao
    abstract val postDao: PostDao
    abstract val postRemoteKeysDao: PostRemoteKeysDao
}