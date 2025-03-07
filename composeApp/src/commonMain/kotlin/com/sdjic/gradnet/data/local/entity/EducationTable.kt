package com.sdjic.gradnet.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "education_table")
data class EducationTable(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val schoolName: String = "",
    val degree: String? = null,
    val field: String? = null,
    val location: String? = null,
    val description: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
)
