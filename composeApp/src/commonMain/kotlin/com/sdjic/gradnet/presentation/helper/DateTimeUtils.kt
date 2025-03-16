package com.sdjic.gradnet.presentation.helper

import com.sdjic.gradnet.presentation.core.model.CalendarDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object DateTimeUtils {

    fun now(): LocalDateTime {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun toEpochMillis(dateTime: LocalDateTime): Long {
        return dateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }


    fun getDaysInMonth(year: Int, month: Month): Int {
        return when (month) {
            Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY, Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31
            Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
            Month.FEBRUARY -> if (isLeapYear(year)) 29 else 28
            else -> 30
        }
    }

    fun getDaysInYear(year: Int): Int {
        return if (isLeapYear(year)) 366 else 365
    }

    fun getCalendarDays(year: Int): List<CalendarDate> {
        val months = Month.entries
        val calendarDays = mutableListOf<CalendarDate>()

        for (month in months) {
            calendarDays.addAll(getCalendarDays(year, month))
        }
        return calendarDays
    }

    fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

    fun getCalendarDays(year: Int, month: Month): List<CalendarDate> {
        val timeZone = TimeZone.currentSystemDefault()
        val daysInMonth = getDaysInMonth(year, month)

        return (1..daysInMonth).map { day ->
            val localDate = LocalDate(year, month, day) // Create LocalDate directly
            val dateTime = localDate.atStartOfDayIn(timeZone).toLocalDateTime(timeZone)
            val dayOfWeek = dateTime.date.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }

            CalendarDate(day, dayOfWeek, month,localDate)
        }
    }

    suspend fun getTimeAgoAsync(postedTimestamp: Long): String = withContext(Dispatchers.Default) {
    val now = toEpochMillis(now())
        val diff = now - postedTimestamp

        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return@withContext when {
            seconds < 60 -> "Just now"
            minutes < 60 -> "$minutes min ago"
            hours < 24 -> "$hours hr ago"
            days < 7 -> "$days days ago"
            days < 30 -> "${days / 7} weeks ago"
            days < 365 -> "${days / 30} months ago"
            else -> "${days / 365} years ago"
        }
    }

    suspend fun parseDateAsync(dateString: String): LocalDateTime = withContext(Dispatchers.Default) {
        try {
            val instant = Instant.parse("${dateString}Z")
            return@withContext instant.toLocalDateTime(TimeZone.currentSystemDefault())

        } catch (e: Exception) {
            val localDateTime = LocalDateTime.parse(dateString)
            return@withContext localDateTime
        }
    }
}