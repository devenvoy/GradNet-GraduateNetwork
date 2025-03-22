package com.sdjic.gradnet.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdjic.gradnet.presentation.composables.animatedlist.AnimatedInfiniteLazyRow
import com.sdjic.gradnet.presentation.core.model.CalendarDate
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.datetime.LocalDateTime

@OptIn(FlowPreview::class)
@Composable
fun AnimatedCalendar(
    daysList: List<CalendarDate>,
    todayDate: LocalDateTime? = null,
    selectedDay: CalendarDate? = null,
    debounceTime: Long = 500L,
    onDaySelected: (CalendarDate) -> Unit
) {

    val initialSelectedIndex by remember(selectedDay) {
        derivedStateOf {
            val selectedDate = daysList.firstOrNull {
                it.day == selectedDay?.day && it.month == selectedDay.month
            }
            daysList.indexOf(selectedDate)
        }
    }

    var selectedIndex by remember { mutableStateOf(initialSelectedIndex) }

    LaunchedEffect(selectedIndex) {
        snapshotFlow { selectedIndex }
            .debounce(debounceTime)
            .collect { index ->
                daysList.getOrNull(index)?.let { onDaySelected(it) }
            }
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
        initialFirstVisibleIndex = initialSelectedIndex - 2,
    ) { animationProgress, index, date, size, lazyListState ->

        if (index == animationProgress.itemIndex) {
            selectedIndex = index
        }

        Box(
            modifier = Modifier
                .width(size)
                .height(size * 1.3f)
                .graphicsLayer {
                    scaleX = animationProgress.scale
                    scaleY = animationProgress.scale
                }
                .clip(RoundedCornerShape(16.dp))
                .background(if (index == selectedIndex) Color.Blue else Color.Transparent)
                .border(2.dp, animationProgress.color, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = date.dayOfWeek.substring(0..<3).uppercase(),
                    fontSize = 14.sp,
                    color = animationProgress.color
                )
                Text(
                    text = date.day.toString(),
                    fontSize = 18.sp,
                    color = animationProgress.color,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = date.month.name.lowercase().replaceFirstChar { it.uppercase() },
                    fontSize = 10.sp,
                    color = animationProgress.color
                )
            }
            todayDate?.let { it ->
                if (date.compareWithLocalDateTime(it)) {
                    Icon(
                        modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).size(14.dp),
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = if (index == selectedIndex) Color.White else animationProgress.color
                    )
                }
            }
        }
    }
}
