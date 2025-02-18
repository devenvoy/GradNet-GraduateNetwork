package com.maxkeppeler.sheets.calendar.models

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

expect sealed class FormatLocale {

    val firstDayOfWeek: DayOfWeek

    fun getDayOfWeekLabels(): Map<DayOfWeek, String>
    fun getMonthShort(date: LocalDate): String

    class CHINESE : FormatLocale
    class JAPANESE : FormatLocale
    class Default : FormatLocale

    companion object {
        fun getDefault(): FormatLocale
    }
}