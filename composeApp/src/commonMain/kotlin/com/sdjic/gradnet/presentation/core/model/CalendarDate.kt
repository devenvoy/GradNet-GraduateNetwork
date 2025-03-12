package com.sdjic.gradnet.presentation.core.model

import kotlinx.datetime.Month

data class CalendarDate(
    val day: Int,
    val dayOfWeek: String,
    val month: Month,
)
