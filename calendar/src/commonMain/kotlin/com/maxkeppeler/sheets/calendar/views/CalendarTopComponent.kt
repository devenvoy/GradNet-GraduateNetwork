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
@file:OptIn(ExperimentalAnimationGraphicsApi::class)

package com.maxkeppeler.sheets.calendar.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.utils.TestTags
import com.maxkeppeker.sheets.core.utils.testTags
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarDisplayMode
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.number
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import sheets_compose_dialogs.calendar.generated.resources.*
import sheets_compose_dialogs.calendar.generated.resources.Res
import sheets_compose_dialogs.calendar.generated.resources.scd_calendar_dialog_prev_month
import sheets_compose_dialogs.calendar.generated.resources.scd_calendar_dialog_prev_week
import sheets_compose_dialogs.calendar.generated.resources.scd_calendar_dialog_select_month
import kotlin.math.max
import kotlin.math.min

/**
 * Top header component of the calendar dialog.
 * @param config The general configuration for the dialog.
 * @param mode The display mode of the dialog.
 * @param navigationDisabled Whenever the navigation of the navigation is disabled.
 * @param prevDisabled Whenever the navigation to the previous period is disabled.
 * @param nextDisabled Whenever the navigation to the next period is disabled.
 * @param cameraDate The current camera-date of the month view.
 * @param onPrev The listener that is invoked when the navigation to the previous period is invoked.
 * @param onNext The listener that is invoked when the navigation to the next period is invoked.
 * @param onMonthClick The listener that is invoked when the month selection was clicked.
 * @param onYearClick The listener that is invoked when the year selection was clicked.
 */
@OptIn(ExperimentalResourceApi::class, FormatStringsInDatetimeFormats::class)
@ExperimentalMaterial3Api
@Composable
internal fun CalendarTopComponent(
    modifier: Modifier,
    config: CalendarConfig,
    mode: CalendarDisplayMode,
    navigationDisabled: Boolean,
    prevDisabled: Boolean,
    nextDisabled: Boolean,
    cameraDate: LocalDate,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    monthSelectionEnabled: Boolean,
    yearSelectionEnabled: Boolean,
    onMonthClick: () -> Unit,
    onYearClick: () -> Unit,
) {

    val enterTransition = expandIn(expandFrom = Alignment.Center, clip = false) + fadeIn()
    val exitTransition = shrinkOut(shrinkTowards = Alignment.Center, clip = false) + fadeOut()

    var chevronMonthAtEnd by remember { mutableStateOf(false) }
    var chevronYearAtEnd by remember { mutableStateOf(false) }

    LaunchedEffect(mode) {
        when (mode) {
            CalendarDisplayMode.CALENDAR -> {
                chevronMonthAtEnd = false
                chevronYearAtEnd = false
            }

            CalendarDisplayMode.MONTH -> chevronYearAtEnd = false
            CalendarDisplayMode.YEAR -> chevronMonthAtEnd = false
        }
    }

    val selectableContainerModifier = Modifier.clip(MaterialTheme.shapes.extraSmall)
    val selectableItemModifier = Modifier
        .padding(start = 8.dp)
        .padding(vertical = 4.dp)
        .padding(end = 4.dp)

    Box(modifier = modifier) {

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.CenterStart),
            visible = !navigationDisabled && !prevDisabled,
            enter = enterTransition,
            exit = exitTransition
        ) {
            Column(Modifier.align(Alignment.CenterStart)) {
                FilledIconButton(
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    modifier = Modifier
                        .testTags(TestTags.CALENDAR_PREVIOUS_ACTION)
                        .size(32.dp),
                    enabled = !navigationDisabled && !prevDisabled,
                    onClick = onPrev
                ) {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        imageVector = config.icons.ChevronLeft,
                        contentDescription = stringResource(
                            when (config.style) {
                                CalendarStyle.MONTH -> Res.string.scd_calendar_dialog_prev_month
                                CalendarStyle.WEEK -> Res.string.scd_calendar_dialog_prev_week
                            }
                        )
                    )
                }

            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = selectableContainerModifier
                    .clickable(config.monthSelection && monthSelectionEnabled) {
                        if (config.monthSelection) {
                            chevronMonthAtEnd = !chevronMonthAtEnd
                        }
                        onMonthClick()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = selectableItemModifier
                        .testTags(TestTags.CALENDAR_MONTH_TITLE, cameraDate.month.number),
                    text = config.locale.getMonthShort(cameraDate),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )
                if (config.monthSelection && monthSelectionEnabled) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription = stringResource(Res.string.scd_calendar_dialog_select_month),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Row(
                modifier = selectableContainerModifier
                    .clickable(config.yearSelection && yearSelectionEnabled) {
                        if (config.yearSelection) {
                            chevronYearAtEnd = !chevronYearAtEnd
                        }
                        onYearClick()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                val formatter = remember {
                    LocalDate.Format {
                        byUnicodePattern("yyyy")
                    }
                }

                Text(
                    modifier = selectableItemModifier
                        .testTags(TestTags.CALENDAR_YEAR_TITLE, cameraDate.year),
                    text = cameraDate.format(formatter),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )
                if (config.yearSelection && yearSelectionEnabled) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription = stringResource(Res.string.scd_calendar_dialog_select_year),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.CenterEnd),
            visible = !navigationDisabled && !nextDisabled,
            enter = enterTransition,
            exit = exitTransition
        ) {
            Column(Modifier.align(Alignment.CenterEnd)) {
                FilledIconButton(
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    modifier = Modifier
                        .testTags(TestTags.CALENDAR_NEXT_ACTION)
                        .size(32.dp),
                    enabled = !navigationDisabled && !nextDisabled,
                    onClick = onNext
                ) {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        imageVector = config.icons.ChevronRight,
                        contentDescription = stringResource(
                            when (config.style) {
                                CalendarStyle.MONTH -> Res.string.scd_calendar_dialog_next_month
                                CalendarStyle.WEEK -> Res.string.scd_calendar_dialog_next_week
                            }
                        )
                    )
                }
            }
        }
    }
}

/**
 * Top header component of the calendar dialog.
 * @param config The general configuration for the dialog.
 * @param mode The display mode of the dialog.
 * @param navigationDisabled Whenever the navigation of the navigation is disabled.
 * @param prevDisabled Whenever the navigation to the previous period is disabled.
 * @param nextDisabled Whenever the navigation to the next period is disabled.
 * @param cameraDate The current camera-date of the month view.
 * @param onPrev The listener that is invoked when the navigation to the previous period is invoked.
 * @param onNext The listener that is invoked when the navigation to the next period is invoked.
 * @param onMonthClick The listener that is invoked when the month selection was clicked.
 * @param onYearClick The listener that is invoked when the year selection was clicked.
 */
@OptIn(ExperimentalResourceApi::class, FormatStringsInDatetimeFormats::class)
@ExperimentalMaterial3Api
@Composable
internal fun CalendarTopLandscapeComponent(
    modifier: Modifier,
    config: CalendarConfig,
    mode: CalendarDisplayMode,
    navigationDisabled: Boolean,
    prevDisabled: Boolean,
    nextDisabled: Boolean,
    cameraDate: LocalDate,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onMonthClick: () -> Unit,
    onYearClick: () -> Unit,
) {

    val enterTransition = expandIn(expandFrom = Alignment.Center, clip = false) + fadeIn()
    val exitTransition = shrinkOut(shrinkTowards = Alignment.Center, clip = false) + fadeOut()

    var chevronMonthAtEnd by remember { mutableStateOf(false) }
    var chevronYearAtEnd by remember { mutableStateOf(false) }

    LaunchedEffect(mode) {
        when (mode) {
            CalendarDisplayMode.CALENDAR -> {
                chevronMonthAtEnd = false
                chevronYearAtEnd = false
            }

            CalendarDisplayMode.MONTH -> chevronYearAtEnd = false
            CalendarDisplayMode.YEAR -> chevronMonthAtEnd = false
        }
    }

    val selectableContainerModifier = Modifier
        .fillMaxWidth()
        .clip(MaterialTheme.shapes.extraSmall)

    val selectableItemModifier = Modifier
        .padding(start = 8.dp)
        .padding(vertical = 4.dp)
        .padding(end = 4.dp)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {

        Row(
            modifier = selectableContainerModifier
                .clickable(config.yearSelection) {
                    if (config.yearSelection) {
                        chevronYearAtEnd = !chevronYearAtEnd
                    }
                    onYearClick()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            val formatter = remember {
                LocalDate.Format {
                    byUnicodePattern("yyyy")
                }
            }

            Text(
                modifier = selectableItemModifier.weight(1f),
                text = cameraDate.format(formatter),
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Start
            )
            if (config.yearSelection) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = stringResource(Res.string.scd_calendar_dialog_select_year),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(Modifier.height(4.dp))

        Row(
            modifier = selectableContainerModifier
                .clickable(config.monthSelection) {
                    if (config.monthSelection) {
                        chevronMonthAtEnd = !chevronMonthAtEnd
                    }
                    onMonthClick()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = selectableItemModifier.weight(1f),
                text = config.locale.getMonthShort(cameraDate),
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Start
            )
            if (config.monthSelection) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = stringResource(Res.string.scd_calendar_dialog_select_month),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        ) {

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.CenterVertically),
                visible = !navigationDisabled && !prevDisabled,
                enter = enterTransition,
                exit = exitTransition
            ) {
                Column(Modifier.align(Alignment.CenterVertically)) {
                    FilledIconButton(
                        colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier
                            .size(32.dp),
                        enabled = !navigationDisabled && !prevDisabled,
                        onClick = onPrev
                    ) {
                        Icon(
                            modifier = Modifier.size(48.dp),
                            imageVector = config.icons.ChevronLeft,
                            contentDescription = stringResource(
                                when (config.style) {
                                    CalendarStyle.MONTH -> Res.string.scd_calendar_dialog_prev_month
                                    CalendarStyle.WEEK -> Res.string.scd_calendar_dialog_prev_week
                                }
                            )
                        )
                    }

                }
            }

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.CenterVertically),
                visible = !navigationDisabled && !nextDisabled,
                enter = enterTransition,
                exit = exitTransition
            ) {
                Column(Modifier.align(Alignment.CenterVertically)) {
                    FilledIconButton(
                        colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                        modifier = Modifier.size(32.dp),
                        enabled = !navigationDisabled && !nextDisabled,
                        onClick = onNext
                    ) {
                        Icon(
                            modifier = Modifier.size(48.dp),
                            imageVector = config.icons.ChevronRight,
                            contentDescription = stringResource(
                                when (config.style) {
                                    CalendarStyle.MONTH -> Res.string.scd_calendar_dialog_next_month
                                    CalendarStyle.WEEK -> Res.string.scd_calendar_dialog_next_week
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}