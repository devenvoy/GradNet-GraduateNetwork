package com.sdjic.gradnet.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdjic.gradnet.presentation.composables.animatedlist.AnimatedInfiniteLazyRow
import com.sdjic.gradnet.presentation.helper.DateTimeUtils.getCalendarDates
import kotlinx.datetime.Month

@Composable
fun AnimatedCalendar(
    year: Int,
    month: Month,
    selectedDay: Int,
    onDaySelected: (Int) -> Unit
) {

    val daysList by remember {
        derivedStateOf {
            getCalendarDates(year, month)
        }
    }

    val initialSelectedIndex = remember(selectedDay) {
        val selectedDate = daysList.first { it.day == selectedDay }
        daysList.indexOf(selectedDate)
    }

    AnimatedInfiniteLazyRow(
        items = daysList,
        inactiveItemPercent = 80,
        visibleItemCount = 5,
        spaceBetweenItems = 8.dp,
        selectorIndex = 2, // Center item
        itemScaleRange = 1, // Scaling range
        activeColor = Color.White,
        inactiveColor = Color.Gray,
        initialFirstVisibleIndex = initialSelectedIndex - 2
    ) { animationProgress, index, date, size, lazyListState ->

        val isSelected = index == animationProgress.itemIndex
        Box(
            modifier = Modifier
                .width(size)
                .height(size*1.3f)
                .graphicsLayer {
                    scaleX = animationProgress.scale
                    scaleY = animationProgress.scale
                }
                .clip(RoundedCornerShape(16.dp))
                .background(if (isSelected) Color.Blue else Color.Transparent)
                .border(2.dp, animationProgress.color, RoundedCornerShape(16.dp))
                .clickable { onDaySelected(date.day) },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = date.day.toString(),
                    fontSize = 18.sp,
                    color = animationProgress.color,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = date.dayOfWeek.substring(0..<3).uppercase(),
                    fontSize = 14.sp,
                    color = animationProgress.color
                )
            }
        }
    }
}
