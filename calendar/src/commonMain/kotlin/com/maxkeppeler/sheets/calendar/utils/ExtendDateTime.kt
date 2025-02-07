package com.maxkeppeler.sheets.calendar.utils

import com.maxkeppeler.sheets.calendar.models.*
import com.maxkeppeler.sheets.calendar.models.CalendarMonthData
import kotlinx.datetime.*
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

internal fun LocalDate.Companion.now(): LocalDate {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
}

class LocalDateRange(
    override val start: LocalDate,
    override val endInclusive: LocalDate
) : ClosedRange<LocalDate>, OpenEndRange<LocalDate> {

    override val endExclusive: LocalDate
        get() = endInclusive.plus(1, DateTimeUnit.DAY)

    override fun contains(value: LocalDate): Boolean {
        return value in start..endInclusive
    }

    override fun isEmpty(): Boolean {
        return start > endInclusive
    }

}

internal fun LocalDate.withYear(value: Int): LocalDate = LocalDate(
    year = value,
    month = this.month,
    dayOfMonth = this.dayOfMonth
)

internal fun LocalDate.withMonth(value: Month): LocalDate = LocalDate(
    year = this.year,
    month = value,
    dayOfMonth = this.dayOfMonth,
)

internal fun LocalDate.withMonth(value: Int): LocalDate = LocalDate(
    year = this.year,
    monthNumber = value,
    dayOfMonth = this.dayOfMonth,
)

internal fun LocalDate.withDayOfMonth(value: Int): LocalDate = LocalDate(
    year = this.year,
    month = this.month,
    dayOfMonth = value
)

internal fun LocalDate.plusYears(value: Int) = this.plus(value, DateTimeUnit.YEAR)

internal fun LocalDate.minusYears(value: Int) = this.minus(value, DateTimeUnit.YEAR)

internal fun LocalDate.plusMonths(value: Int) = this.plus(value, DateTimeUnit.MONTH)

internal fun LocalDate.minusMonths(value: Int) = this.minus(value, DateTimeUnit.MONTH)

internal fun LocalDate.plusWeeks(value: Int) = this.plus(value, DateTimeUnit.WEEK)

internal fun LocalDate.minusWeeks(value: Int) = this.minus(value, DateTimeUnit.WEEK)

internal fun LocalDate.plusDays(value: Int) = this.plus(value, DateTimeUnit.DAY)

internal fun LocalDate.minusDays(value: Int) = this.minus(value, DateTimeUnit.DAY)

internal val LocalDate.startOfWeek: LocalDate
    get() = minusDays(dayOfWeek.isoDayNumber - 1)

internal val LocalDate.endOfWeek: LocalDate
    get() = plusDays(7 - dayOfWeek.isoDayNumber)

/**
 * Extension function that jumps to the first day of the month.
 *
 * @return [LocalDate] representing the first day of the month
 */
internal val LocalDate.startOfWeekOrMonth: LocalDate
    get() {
        var result = this
        while (result.dayOfMonth > 1 && result.dayOfWeek != DayOfWeek.MONDAY) {
            result = result.minusDays(1)
        }
        return result
    }

internal val LocalDate.startOfMonth: LocalDate
    get() = withDayOfMonth(1)

/**
 * Extension function that checks if year is a leap year.
 *
 * Example: 2016, 2020, 2024, etc
 *
 * @return [Boolean] whether year is a leap year
 */
internal val LocalDate.isLeapYear: Boolean
    get() {
        var isLeapYear = year % 4 == 0
        isLeapYear = isLeapYear && (year % 100 != 0 || year % 400 == 0)

        return isLeapYear
    }

/**
 * Extension function that gets the maximum days of month
 *
 * @return [Int] of days in month
 */
internal fun Month.length(leapYear: Boolean): Int = when (this) {
    Month.JANUARY -> 31
    Month.FEBRUARY -> {
        if (leapYear) {
            29
        } else {
            28
        }
    }
    Month.MARCH -> 31
    Month.APRIL -> 30
    Month.MAY -> 31
    Month.JUNE -> 30
    Month.JULY -> 31
    Month.AUGUST -> 31
    Month.SEPTEMBER -> 30
    Month.OCTOBER -> 31
    Month.NOVEMBER -> 30
    Month.DECEMBER -> 31
    else -> 30
}

internal fun LocalDate.with(dayOfWeek: DayOfWeek): LocalDate {
    var result = this
    while (result.dayOfWeek != dayOfWeek) {
        result = result.minusDays(1)
    }
    return result
}

/**
 * Extension function that gets the maximum days of month
 *
 * @return [Int] of days in month
 */
internal val LocalDate.lengthOfMonth: Int
    get() = month.length(isLeapYear)

/**

Extension function that jumps to the last day of the month.
@return [LocalDate] representing the last day of the month
 */
internal val LocalDate.endOfMonth: LocalDate
    get() = withDayOfMonth(lengthOfMonth)

/**
 * Get the first day of the previous week from the current date.
 *
 * Skips in current week to previous month's Monday if day is the first day of the month and not Monday.
 * Skips to previous week if the current day of the month is greater than or equal to 7 or is the first day of the month and is Monday.
 * Skips to the first day of the previous month otherwise.
 * @return The first day of the previous week as a `LocalDate` object.
 */
internal val LocalDate.previousWeek: LocalDate
    get() = when {
        dayOfMonth == Constants.FIRST_DAY_IN_MONTH
                && dayOfWeek != DayOfWeek.MONDAY -> with(DayOfWeek.MONDAY)

        dayOfMonth >= Constants.DAYS_IN_WEEK
                || dayOfMonth == Constants.FIRST_DAY_IN_MONTH
                && dayOfWeek == DayOfWeek.MONDAY -> minusWeeks(1)

        else -> withDayOfMonth(Constants.FIRST_DAY_IN_MONTH)
    }

/**
 * Get the date of the next week from the current date.
 *
 * The next week is determined based on the current day of the month and the remaining days in the month.
 * If the current day of the month is the first day of the month, it skips to the next Monday.
 * If there are less than 7 days remaining in the current month, it skips to the first day of the next month.
 * @return The first day of the next week as a `LocalDate` object.
 */
internal val LocalDate.nextWeek: LocalDate
    get() = when {
        dayOfMonth == Constants.FIRST_DAY_IN_MONTH -> plusDays((7 - dayOfWeek.isoDayNumber) + 1)
        lengthOfMonth - dayOfMonth >= Constants.DAYS_IN_WEEK -> plusWeeks(1)
        else -> plusMonths(1).withDayOfMonth(Constants.FIRST_DAY_IN_MONTH)
    }

/**
 * Returns a new `LocalDate` instance representing the previous date based on the `CalendarConfig` passed.
 *
 * If `CalendarConfig.style` is set to `CalendarStyle.MONTH`, the function returns the first day of the previous month.
 * If `CalendarConfig.style` is set to `CalendarStyle.WEEK`, the function returns the first day (Monday) of the previous week.
 *
 * @param config The `CalendarConfig` to determine the jump step.
 * @return A new `LocalDate` instance representing the previous date based on the `CalendarConfig`.
 */
internal fun LocalDate.jumpPrev(config: CalendarConfig): LocalDate = when (config.style) {
    CalendarStyle.MONTH -> this.minusMonths(1).withDayOfMonth(1)
    CalendarStyle.WEEK -> this.previousWeek
}

/**
 * Returns a new `LocalDate` instance representing the next date based on the `CalendarConfig` passed.
 *
 * If `CalendarConfig.style` is set to `CalendarStyle.MONTH`, the function returns the first day of the next month.
 * If `CalendarConfig.style` is set to `CalendarStyle.WEEK`, the function returns the first day (Monday) of the next week.
 *
 * @param config The `CalendarConfig` to determine the jump step.
 * @return A new `LocalDate` instance representing the next date based on the `CalendarConfig`.
 */
internal fun LocalDate.jumpNext(config: CalendarConfig): LocalDate = when (config.style) {
    CalendarStyle.MONTH -> this.plusMonths(1).withDayOfMonth(1)
    CalendarStyle.WEEK -> this.nextWeek
}

/**
 * Returns the initial date to be displayed on the CalendarView based on the selection mode.
 * @param selection The selection mode.
 * @param boundary The boundary of the calendar.
 * @return The initial date to be displayed on the CalendarView.
 */
internal fun getInitialCameraDate(
    selection: CalendarSelection,
    boundary: ClosedRange<LocalDate>
): LocalDate {
    val cameraDateBasedOnMode = when (selection) {
        is CalendarSelection.Date -> selection.selectedDate
        is CalendarSelection.Dates -> selection.selectedDates?.firstOrNull()
        is CalendarSelection.Period -> selection.selectedRange?.start
    } ?: run {
        val now = LocalDate.now()
        if (now in boundary) now else boundary.start
    }
    return cameraDateBasedOnMode.startOfWeekOrMonth
}

/**
 * Returns the custom initial date in case the camera date is within the boundary. Otherwise, it returns null.
 *
 * @param cameraDate The initial camera date.
 * @param boundary The boundary of the calendar.
 * @return The initial camera date if it's within the boundary, otherwise null.
 */
internal fun getInitialCustomCameraDate(
    cameraDate: LocalDate?,
    boundary: ClosedRange<LocalDate>
): LocalDate? = cameraDate?.takeIf { it in boundary }?.startOfWeekOrMonth

/**
 * Get selection value of date.
 */
internal val CalendarSelection.dateValue: LocalDate?
    get() = if (this is CalendarSelection.Date) selectedDate else null

/**
 * Get selection value of dates.
 */
internal val CalendarSelection.datesValue: Array<LocalDate>
    get() = if (this is CalendarSelection.Dates) selectedDates.orEmpty().toTypedArray() else emptyArray()

/**
 * Get selection value of range.
 */
internal val CalendarSelection.rangeValue: Array<LocalDate?>
    get() = if (this is CalendarSelection.Period) {
        arrayOf(selectedRange?.start, selectedRange?.endInclusive)
    } else {
        emptyArray()
    }

/**
 * Get range start value.
 */
internal val Iterable<LocalDate?>.startValue: LocalDate?
    get() = this.toList().getOrNull(Constants.RANGE_START)

/**
 * Get range end value.
 */
internal val Iterable<LocalDate?>.endValue: LocalDate?
    get() = this.toList().getOrNull(Constants.RANGE_END)

internal fun LocalDate.isAfter(other: LocalDate): Boolean = this > other

internal fun LocalDate.isBefore(other: LocalDate): Boolean = this < other

/**
 * Calculate the month data based on the camera date and the restrictions.
 */
internal fun calcMonthData(
    config: CalendarConfig,
    cameraDate: LocalDate,
    today: LocalDate = LocalDate.now(),
): CalendarMonthData {
    val months = Month.entries

    val boundaryFilteredMonths = months.filter { month ->
        val maxDaysOfMonth = month.length(cameraDate.isLeapYear)
        val startDay = minOf(config.boundary.start.dayOfMonth, maxDaysOfMonth)
        val endDay = minOf(config.boundary.endInclusive.dayOfMonth, maxDaysOfMonth)
        val cameraDateWithMonth = cameraDate.withMonth(month).withDayOfMonth(startDay)
        cameraDateWithMonth in config.boundary || cameraDateWithMonth.withDayOfMonth(endDay) in config.boundary
    }

    return CalendarMonthData(
        selected = cameraDate.month,
        thisMonth = today.month,
        disabled = months.minus(boundaryFilteredMonths.toSet())
    )
}

/**
 * Calculate the calendar data based on the camera-date.
 */
@OptIn(FormatStringsInDatetimeFormats::class)
internal fun calcCalendarData(
    config: CalendarConfig,
    cameraDate: LocalDate,
): CalendarData {
    var weekCameraDate = cameraDate

    val firstDayOfWeek = config.locale.firstDayOfWeek
    val dayOfWeek = cameraDate.dayOfWeek
    val diff = (dayOfWeek.isoDayNumber - firstDayOfWeek.isoDayNumber + 7) % 7

    val offsetStart = when (config.style) {
        CalendarStyle.MONTH -> diff
        CalendarStyle.WEEK -> {
            val dayOfWeekInMonth = cameraDate.startOfMonth.dayOfWeek
            val dayDiff = (dayOfWeekInMonth.isoDayNumber - firstDayOfWeek.isoDayNumber + 7) % 7

            val adjustedWeekCameraDate = if (cameraDate.dayOfMonth <= Constants.DAYS_IN_WEEK && dayDiff > 0) {
                cameraDate.minusDays((cameraDate.dayOfWeek.isoDayNumber - firstDayOfWeek.isoDayNumber + 7) % 7)
            } else {
                weekCameraDate
            }

            ((adjustedWeekCameraDate.dayOfWeek.isoDayNumber - firstDayOfWeek.isoDayNumber + 7) % 7).also {
                weekCameraDate = adjustedWeekCameraDate
            }
        }
    }

    val days = when (config.style) {
        CalendarStyle.MONTH -> cameraDate.lengthOfMonth
        CalendarStyle.WEEK -> DayOfWeek.entries.size
    }

    val rangedDays = (1..days.plus(offsetStart)).map { dayIndex ->
        val date = when (config.style) {
            CalendarStyle.MONTH -> cameraDate
                .withDayOfMonth(1)
                .plusDays(dayIndex.minus(1))
                .minusDays(offsetStart)

            CalendarStyle.WEEK -> weekCameraDate
                .plusDays(dayIndex.minus(1))
                .minusDays(offsetStart)
        }
        Pair(CalendarViewType.DAY, date)
    }.drop(
        when (config.style) {
            CalendarStyle.MONTH -> offsetStart
            CalendarStyle.WEEK -> 0
        }
    ).dropLast(
        when (config.style) {
            CalendarStyle.MONTH -> 0
            CalendarStyle.WEEK -> offsetStart
        }
    ).toMutableList()

    val isMonthStartOffset = weekCameraDate.dayOfMonth <= 7
    if (isMonthStartOffset) {
        repeat(offsetStart) {
            rangedDays.add(0, Pair(CalendarViewType.DAY_START_OFFSET, LocalDate.now()))
        }
    }

    val chunkedDays: List<List<Pair<CalendarViewType, Any>>> = rangedDays.chunked(Constants.DAYS_IN_WEEK)

    val weekDays: List<List<Pair<CalendarViewType, Any>>> = chunkedDays.map { week ->
        if (config.displayCalendarWeeks) {
            val newWeek = week.toMutableList().apply {
                val firstDateCalendarWeek = week.first { it.first == CalendarViewType.DAY }.second as LocalDate
                val formatter = LocalDate.Format {
                    byUnicodePattern("w")
                }
                val calendarWeek = formatter.format(firstDateCalendarWeek)
                add(0, Pair(CalendarViewType.CW, calendarWeek))
            }
            newWeek
        } else week
    }

    return CalendarData(
        offsetStart = offsetStart,
        weekCameraDate = weekCameraDate,
        cameraDate = cameraDate,
        days = weekDays
    )
}

/**
 * Calculate the calendar date-data based on the date.
 */
internal fun calcCalendarDateData(
    date: LocalDate,
    calendarViewData: CalendarData,
    selection: CalendarSelection,
    config: CalendarConfig,
    selectedDate: LocalDate?,
    selectedDates: List<LocalDate>?,
    selectedRange: Pair<LocalDate?, LocalDate?>
): CalendarDateData? {

    if (date.monthNumber != calendarViewData.cameraDate.monthNumber) return null

    var selectedStartInit = false
    var selectedEnd = false
    var selectedBetween = false
    val selected = when (selection) {
        is CalendarSelection.Date -> selectedDate == date
        is CalendarSelection.Dates -> {
            selectedDates?.contains(date) ?: false
        }
        is CalendarSelection.Period -> {
            val selectedStart = selectedRange.first == date
            selectedStartInit = selectedStart && selectedRange.second != null
            selectedEnd = selectedRange.second == date
            selectedBetween = (selectedRange.first?.let { date.isAfter(it) } ?: false)
                    && selectedRange.second?.let { date.isBefore(it) } ?: false
            selectedBetween || selectedStart || selectedEnd
        }
    }
    val outOfBoundary = date !in config.boundary
    val disabledDate = config.disabledDates?.contains(date) ?: false

    return CalendarDateData(
        date = date,
        disabled = disabledDate,
        disabledPassively = outOfBoundary,
        selected = selected,
        selectedBetween = selectedBetween,
        selectedStart = selectedStartInit,
        selectedEnd = selectedEnd
    )
}
