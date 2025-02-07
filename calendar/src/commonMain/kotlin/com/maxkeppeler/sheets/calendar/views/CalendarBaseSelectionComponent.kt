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

package com.maxkeppeler.sheets.calendar.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.LibOrientation
import com.maxkeppeker.sheets.core.utils.BaseConstants
import com.maxkeppeler.sheets.calendar.models.CalendarDisplayMode
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import sheets_compose_dialogs.calendar.generated.resources.Res
import sheets_compose_dialogs.calendar.generated.resources.scd_calendar_dialog_select_month
import sheets_compose_dialogs.calendar.generated.resources.scd_calendar_dialog_select_year

/**
 * The main component for the selection of the use-case as well as the selection of month and year within the dialog.
 * @param modifier The modifier that is applied to this component.
 * @param yearListState The state of the year list selection view.
 * @param cells The amount of cells / columns that are used for the calendar grid view.
 * @param mode The display mode of the dialog.
 * @param onCalendarView The content that will be displayed if the [CalendarDisplayMode] is in [CalendarDisplayMode.CALENDAR].
 * @param onMonthView The content that will be displayed if the [CalendarDisplayMode] is in [CalendarDisplayMode.MONTH].
 * @param onYearView The content that will be displayed if the [CalendarDisplayMode] is in [CalendarDisplayMode.YEAR].
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
@Composable
internal fun CalendarBaseSelectionComponent(
    modifier: Modifier,
    orientation: LibOrientation,
    yearListState: LazyListState,
    cells: Int,
    mode: CalendarDisplayMode,
    onCalendarView: LazyGridScope.() -> Unit,
    onMonthView: LazyGridScope.() -> Unit,
    onYearView: LazyListScope.() -> Unit
) {

    val baseModifier = modifier.then(
            when (orientation) {
                LibOrientation.PORTRAIT -> Modifier.padding(top = 16.dp)
                LibOrientation.LANDSCAPE -> Modifier.sizeIn(
                    maxHeight = BaseConstants.DYNAMIC_SIZE_MAX,
                    maxWidth = BaseConstants.DYNAMIC_SIZE_MAX
                )
            }
        )


    val selectionModifier = modifier
        .wrapContentHeight()
        .then(
            when (orientation) {
                LibOrientation.PORTRAIT -> Modifier.padding(top = 24.dp)
                LibOrientation.LANDSCAPE -> Modifier
            }
        )

    val baseViewModifier = Modifier
        .padding(top = 16.dp)

    val gridYearModifier = baseViewModifier
        .graphicsLayer { alpha = 0.99F }
        .drawWithContent {
            val colorStops = arrayOf(
                0.0f to Color.Transparent,
                0.25f to Color.Black,
                0.75f to Color.Black,
                1.0f to Color.Transparent
            )
            drawContent()
            drawRect(
                brush = Brush.horizontalGradient(*colorStops),
                blendMode = BlendMode.DstIn
            )
        }

    when (mode) {
        CalendarDisplayMode.CALENDAR -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(cells),
                modifier = baseModifier,
                userScrollEnabled = false,
            ) {
                onCalendarView()
            }
        }
        CalendarDisplayMode.MONTH -> {
            Column(
                modifier = selectionModifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.scd_calendar_dialog_select_month),
                    style = MaterialTheme.typography.titleMedium,
                )
                LazyVerticalGrid(
                    modifier = baseViewModifier,
                    columns = when (orientation) {
                        LibOrientation.PORTRAIT -> GridCells.Fixed(cells)
                        LibOrientation.LANDSCAPE -> GridCells.Adaptive(48.dp)
                    },
                    content = onMonthView
                )
            }
        }
        CalendarDisplayMode.YEAR -> {
            Column(
                modifier = selectionModifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.scd_calendar_dialog_select_year),
                    style = MaterialTheme.typography.titleMedium,
                )
                LazyRow(
                    modifier = gridYearModifier,
                    state = yearListState,
                    flingBehavior = rememberSnapFlingBehavior(yearListState),
                    contentPadding = PaddingValues(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    content = onYearView
                )
            }
        }
    }
}