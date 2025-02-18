package com.maxkeppeler.sheets.calendar.models

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

actual sealed class FormatLocale {

    actual val firstDayOfWeek: DayOfWeek
        get() = DayOfWeek.MONDAY

    actual fun getDayOfWeekLabels(): Map<DayOfWeek, String> {
        return when (this) {
            is CHINESE -> getSimplifiedChineseDayOfWeekLabels()
            is JAPANESE -> getJapaneseDayOfWeekLabels()
            else -> DayOfWeek.entries.associateWith { dayOfWeek -> dayOfWeek.displayName() }
        }
    }

    // ToDo("wait for kotlin datetime with locale parser")
    actual fun getMonthShort(date: LocalDate): String {
        return when (date.month) {
            Month.JANUARY -> "Jan"
            Month.FEBRUARY -> "Feb"
            Month.MARCH -> "Mar"
            Month.APRIL -> "Apr"
            Month.MAY -> "May"
            Month.JUNE -> "Jun"
            Month.JULY -> "Jul"
            Month.AUGUST -> "Aug"
            Month.SEPTEMBER -> "Sep"
            Month.OCTOBER -> "Oct"
            Month.NOVEMBER -> "Nov"
            Month.DECEMBER -> "Dec"
            else -> date.month.name
        }
    }

    internal fun getSimplifiedChineseDayOfWeekLabels(): Map<DayOfWeek, String> = mapOf(
        DayOfWeek.MONDAY to "\u4e00",
        DayOfWeek.TUESDAY to "\u4e8c",
        DayOfWeek.WEDNESDAY to "\u4e09",
        DayOfWeek.THURSDAY to "\u56db",
        DayOfWeek.FRIDAY to "\u4e94",
        DayOfWeek.SATURDAY to "\u516d",
        DayOfWeek.SUNDAY to "\u65e5",
    )

    internal fun getJapaneseDayOfWeekLabels(): Map<DayOfWeek, String> = mapOf(
        DayOfWeek.MONDAY to "\u6708",
        DayOfWeek.TUESDAY to "\u706b",
        DayOfWeek.WEDNESDAY to "\u6c34",
        DayOfWeek.THURSDAY to "\u6728",
        DayOfWeek.FRIDAY to "\u91d1",
        DayOfWeek.SATURDAY to "\u571f",
        DayOfWeek.SUNDAY to "\u65e5",
    )

    private fun DayOfWeek.displayName(): String {
        return when (this) {
            DayOfWeek.MONDAY -> "M"
            DayOfWeek.TUESDAY -> "T"
            DayOfWeek.WEDNESDAY -> "W"
            DayOfWeek.THURSDAY -> "T"
            DayOfWeek.FRIDAY -> "F"
            DayOfWeek.SATURDAY -> "S"
            DayOfWeek.SUNDAY -> "S"
            else -> this.name
        }
    }

    actual data object CHINESE : FormatLocale()
    actual data object JAPANESE : FormatLocale()
    actual data object Default : FormatLocale()

    actual companion object {
        actual fun getDefault(): FormatLocale = Default
    }
}