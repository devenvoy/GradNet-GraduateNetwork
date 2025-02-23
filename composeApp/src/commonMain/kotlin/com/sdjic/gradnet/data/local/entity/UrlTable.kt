package com.sdjic.gradnet.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "url_table")
data class UrlTable(
    @PrimaryKey val id: Long = 0,
    val type: String,
    val url: String
)