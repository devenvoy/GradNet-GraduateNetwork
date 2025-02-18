/*
 *  Copyright (C) 2022-2024. Maximilian Keppeler (https://www.maxkeppeler.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.maxkeppeler.sheets.calendar.utils

import com.maxkeppeler.sheets.calendar.models.FormatLocale
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.isoDayNumber

/**
 * Adjusts the ordering of DayOfWeek values based on the locale's first day of the week.
 *
 * @param locale the locale to adjust for
 * @return an ordered list of DayOfWeek values starting with the locale's first day of the week
 */
internal fun getOrderedDaysOfWeek(locale: FormatLocale): List<DayOfWeek> {
    val firstDayOfWeek = locale.firstDayOfWeek
    val daysOfWeek = DayOfWeek.entries
    val orderedDays = daysOfWeek.sortedBy { (it.isoDayNumber - firstDayOfWeek.isoDayNumber + 7) % 7 }
    return orderedDays
}

/**
 * Integrates the ordered days of the week with their corresponding labels.
 *
 * @param locale the locale to get labels and order for
 * @return a linked map of ordered day of week labels
 */
internal fun getOrderedDayOfWeekLabels(locale: FormatLocale): LinkedHashMap<DayOfWeek, String> {
    val dayLabels = locale.getDayOfWeekLabels()
    val orderedDays = getOrderedDaysOfWeek(locale)
    return linkedMapOf<DayOfWeek, String>().apply {
        orderedDays.forEach { day ->
            this[day] = dayLabels[day] ?: ""
        }
    }
}
