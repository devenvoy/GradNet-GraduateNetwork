package com.sdjic.gradnet.presentation.core.model

import com.maxkeppeker.sheets.core.utils.JvmSerializable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month

data class CalendarDate(
    val day: Int,
    val dayOfWeek: String,
    val month: Month,
    val localDate: LocalDate
) : JvmSerializable{

    fun compareWithLocalDateTime(localDateTime: LocalDateTime): Boolean {
        return localDateTime.date == localDate
    }
}
