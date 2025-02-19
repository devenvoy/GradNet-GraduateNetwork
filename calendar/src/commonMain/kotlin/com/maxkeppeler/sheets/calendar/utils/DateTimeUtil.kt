package com.maxkeppeler.sheets.calendar.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

object DateTimeUtil {
    fun now(): LocalDate {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    }

    fun LocalDate.toEpochMillis(): Long {
        return atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }

    fun Long.toLocalDate(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDate {
        return Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone).date
    }
}











