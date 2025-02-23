package com.sdjic.gradnet.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "experience_table")
data class ExperienceTable(
    @PrimaryKey val id: Long = 0,
    val title: String = "",
    val type: String? = null,
    val company: String? = null,
    val location: String? = null,
    val description: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
)