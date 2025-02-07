package com.maxkeppeler.sheets.calendar.models

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale

actual sealed class FormatLocale(private val locale: Locale) {

    actual val firstDayOfWeek: DayOfWeek
        get() = WeekFields.of(locale).firstDayOfWeek

    actual open fun getDayOfWeekLabels(): Map<DayOfWeek, String> {
        return when (this) {
            is CHINESE -> getSimplifiedChineseDayOfWeekLabels()
            is JAPANESE -> getJapaneseDayOfWeekLabels()
            else -> {
                DayOfWeek.entries.associateWith { dayOfWeek ->
                    dayOfWeek.getDisplayName(TextStyle.NARROW, locale)
                }
            }
        }
    }

    actual open fun getMonthShort(date: LocalDate): String {
        return date.toJavaLocalDate().format(DateTimeFormatter.ofPattern("MMM"))
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

    actual data class CHINESE(private val locale: Locale) : FormatLocale(locale) {
        override fun getDayOfWeekLabels(): Map<DayOfWeek, String> {
            return getSimplifiedChineseDayOfWeekLabels()
        }
    }

    actual data class JAPANESE(private val locale: Locale) : FormatLocale(locale) {
        override fun getDayOfWeekLabels(): Map<DayOfWeek, String> {
            return getJapaneseDayOfWeekLabels()
        }
    }

    actual data class Default(private val locale: Locale) : FormatLocale(locale)

    actual companion object {
        actual fun getDefault(): FormatLocale {
            val defaultLocale = Locale.getDefault()

            return when {
                Locale.SIMPLIFIED_CHINESE.let {
                    defaultLocale.language == it.language
                } -> CHINESE(defaultLocale)

                Locale.JAPANESE.let {
                    defaultLocale.language == it.language
                } -> JAPANESE(defaultLocale)

                else -> Default(defaultLocale)
            }
        }
    }
}