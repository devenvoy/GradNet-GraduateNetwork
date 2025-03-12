package com.sdjic.gradnet.presentation.core.model

data class CalendarDate(
    val day: Int,
    val dayOfWeek: String,
    val isSelected: Boolean = false
)
