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
package com.maxkeppeler.sheets.calendar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.BaseBehaviors
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.LibOrientation
import com.maxkeppeker.sheets.core.models.base.StateHandler
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.views.ButtonsComponent
import com.maxkeppeker.sheets.core.views.base.FrameBase
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarDisplayMode
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.calendar.utils.endValue
import com.maxkeppeler.sheets.calendar.utils.getOrderedDayOfWeekLabels
import com.maxkeppeler.sheets.calendar.utils.startValue
import com.maxkeppeler.sheets.calendar.views.CalendarBaseSelectionComponent
import com.maxkeppeler.sheets.calendar.views.CalendarTopComponent
import com.maxkeppeler.sheets.calendar.views.CalendarTopLandscapeComponent
import com.maxkeppeler.sheets.calendar.views.setupCalendarSelectionView
import com.maxkeppeler.sheets.calendar.views.setupMonthSelectionView
import com.maxkeppeler.sheets.calendar.views.setupYearSelectionView
import kotlinx.datetime.LocalDate

/**
 * Calendar dialog for the use-case to select a date or period in a typical calendar-view.
 * @param useCaseState The state of the sheet.
 * @param selection The selection configuration for the dialog view.
 * @param config The general configuration for the dialog view.
 * @param header The header to be displayed at the top of the dialog view.
 */
@ExperimentalMaterial3Api
@Composable
fun CalendarView(
    useCaseState: UseCaseState,
    selection: CalendarSelection,
    config: CalendarConfig = CalendarConfig(),
    header: Header? = null,
) {
    val calendarState = rememberCalendarState(selection, config)
    StateHandler(useCaseState, calendarState)

    val coroutine = rememberCoroutineScope()
    val onSelection: (LocalDate) -> Unit = {
        calendarState.processSelection(it)
        BaseBehaviors.autoFinish(
            selection = selection,
            coroutine = coroutine,
            onSelection = calendarState::onFinish,
            onFinished = useCaseState::finish,
            onDisableInput = calendarState::disableInput
        )
    }

    val yearListState = rememberLazyListState()
    LaunchedEffect(calendarState.mode) {
        if (calendarState.mode == CalendarDisplayMode.YEAR) {
            yearListState.scrollToItem(calendarState.yearIndex)
        }
    }

    val weekdays = remember { getOrderedDayOfWeekLabels(config.locale) }

    val density = LocalDensity.current
    FrameBase(
        header = header,
        config = config,
        layoutHorizontalAlignment = Alignment.CenterHorizontally,
        layout = {
            CalendarTopComponent(
                modifier = Modifier.fillMaxWidth(),
                config = config,
                mode = calendarState.mode,
                navigationDisabled = calendarState.mode != CalendarDisplayMode.CALENDAR,
                prevDisabled = calendarState.isPrevDisabled,
                nextDisabled = calendarState.isNextDisabled,
                cameraDate = calendarState.cameraDate,
                onPrev = calendarState::onPrevious,
                onNext = calendarState::onNext,
                monthSelectionEnabled = calendarState.isMonthSelectionEnabled,
                onMonthClick = calendarState::onMonthSelectionClick,
                yearSelectionEnabled = calendarState.isYearSelectionEnabled,
                onYearClick = calendarState::onYearSelectionClick,
            )
            CalendarBaseSelectionComponent(
                modifier = Modifier.wrapContentHeight(),
                orientation = LibOrientation.PORTRAIT,
                yearListState = yearListState,
                mode = calendarState.mode,
                cells = calendarState.cells,
                onCalendarView = {
                    setupCalendarSelectionView(
                        dayOfWeekLabels = weekdays,
                        cells = calendarState.cells,
                        orientation = LibOrientation.PORTRAIT,
                        config = config,
                        selection = selection,
                        data = calendarState.calendarData,
                        onSelect = onSelection,
                        selectedDate = calendarState.date.value,
                        selectedDates = calendarState.dates,
                        selectedRange = Pair(
                            calendarState.range.startValue,
                            calendarState.range.endValue
                        ),
                    )
                },
                onMonthView = {
                    setupMonthSelectionView(
                        locale = config.locale,
                        monthsData = calendarState.monthsData,
                        onMonthClick = calendarState::onMonthClick
                    )
                },
                onYearView = {
                    setupYearSelectionView(
                        yearsRange = calendarState.yearsRange,
                        selectedYear = calendarState.cameraDate.year,
                        onYearClick = calendarState::onYearClick
                    )
                }
            )
        },
        layoutLandscapeVerticalAlignment = Alignment.Top,
        layoutLandscape = when (config.style) {
            CalendarStyle.MONTH -> {
                {
                    var calendarHeight by remember { mutableIntStateOf(0) }
                    CalendarTopLandscapeComponent(
                        modifier = Modifier
                            .weight(0.3f)
                            .height(density.run { calendarHeight.toDp() }),
                        config = config,
                        mode = calendarState.mode,
                        navigationDisabled = calendarState.mode != CalendarDisplayMode.CALENDAR,
                        prevDisabled = calendarState.isPrevDisabled,
                        nextDisabled = calendarState.isNextDisabled,
                        cameraDate = calendarState.cameraDate,
                        onPrev = calendarState::onPrevious,
                        onNext = calendarState::onNext,
                        onMonthClick = { calendarState.onMonthSelectionClick() },
                        onYearClick = { calendarState.onYearSelectionClick() },
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    CalendarBaseSelectionComponent(
                        modifier = Modifier
                            .weight(0.7f)
                            .onGloballyPositioned { coordinates ->
                                if (calendarHeight != coordinates.size.height)
                                    calendarHeight = coordinates.size.height
                            },
                        orientation = LibOrientation.LANDSCAPE,
                        yearListState = yearListState,
                        cells = calendarState.cells,
                        mode = calendarState.mode,
                        onCalendarView = {
                            setupCalendarSelectionView(
                                dayOfWeekLabels = weekdays,
                                cells = calendarState.cells,
                                orientation = LibOrientation.LANDSCAPE,
                                config = config,
                                selection = selection,
                                data = calendarState.calendarData,
                                onSelect = onSelection,
                                selectedDate = calendarState.date.value,
                                selectedDates = calendarState.dates,
                                selectedRange = Pair(
                                    calendarState.range.startValue,
                                    calendarState.range.endValue
                                ),
                            )
                        },
                        onMonthView = {
                            setupMonthSelectionView(
                                locale = config.locale,
                                monthsData = calendarState.monthsData,
                                onMonthClick = calendarState::onMonthClick
                            )
                        },
                        onYearView = {
                            setupYearSelectionView(
                                yearsRange = calendarState.yearsRange,
                                selectedYear = calendarState.cameraDate.year,
                                onYearClick = calendarState::onYearClick
                            )
                        },
                    )
                }
            }
            CalendarStyle.WEEK -> null
        },
        buttonsVisible = selection.withButtonView && calendarState.mode == CalendarDisplayMode.CALENDAR
    ) { orientation ->
        ButtonsComponent(
            orientation = orientation,
            selection = selection,
            onPositiveValid = calendarState.valid,
            onNegative = { selection.onNegativeClick?.invoke() },
            onPositive = calendarState::onFinish,
            state = useCaseState,
        )
    }
}