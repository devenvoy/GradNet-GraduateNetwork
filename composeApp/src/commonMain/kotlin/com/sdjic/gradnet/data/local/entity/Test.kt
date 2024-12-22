package com.sdjic.gradnet.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Test(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)