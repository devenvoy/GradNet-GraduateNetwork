@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.sdjic.gradnet.di.platform_di

import androidx.room.RoomDatabaseConstructor
import com.sdjic.gradnet.data.local.room.GradNetDB

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<GradNetDB> {
    override fun initialize(): GradNetDB
}