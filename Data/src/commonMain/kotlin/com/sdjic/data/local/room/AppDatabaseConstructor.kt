
package com.sdjic.data.local.room

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<GradNetDB> {
    override fun initialize(): GradNetDB
}