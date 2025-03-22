package com.sdjic.gradnet.presentation.screens.event

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import co.touchlab.kermit.Logger
import com.sdjic.gradnet.data.network.entity.dto.EventDto
import com.sdjic.gradnet.presentation.composables.AnimatedCalendar
import com.sdjic.gradnet.presentation.composables.images.BannerWidget
import com.sdjic.gradnet.presentation.composables.text.ExpandableText
import com.sdjic.gradnet.presentation.composables.text.Label
import com.sdjic.gradnet.presentation.composables.text.Title
import com.sdjic.gradnet.presentation.core.DummyBgImage
import com.sdjic.gradnet.presentation.core.model.CalendarDate
import com.sdjic.gradnet.presentation.helper.AutoSwipePagerEffect
import com.sdjic.gradnet.presentation.helper.DateTimeUtils
import com.sdjic.gradnet.presentation.helper.LocalRootNavigator
import com.sdjic.gradnet.presentation.helper.koinScreenModel
import com.sdjic.gradnet.presentation.helper.shimmerLoadingAnimation
import com.sdjic.gradnet.presentation.theme.AppTheme
import com.sdjic.gradnet.presentation.theme.displayFontFamily
import kotlinx.datetime.Month
import kotlinx.datetime.number
import network.chaintech.sdpcomposemultiplatform.ssp

class EventScreen : Screen {
    @Composable
    override fun Content() {
        AppTheme { EventScreenContent() }
    }

    @Composable
    fun EventScreenContent() {
        val eventScreenModel = koinScreenModel<EventScreenModel>()
        val navigator = LocalRootNavigator.current
        val trendingEventItems by eventScreenModel.trendingEventList.collectAsState()
        val selectedDateEvents by eventScreenModel.selectedDateEventsList.collectAsState()
        val todayDate by eventScreenModel.todayDate.collectAsState()
        val selectedDay by eventScreenModel.selectedDay.collectAsState()

        val daysList by remember {
            derivedStateOf {
                todayDate.month.let {
                    val list = mutableListOf<CalendarDate>()
                    list.addAll(
                        DateTimeUtils.getCalendarDays(
                            todayDate.year, Month(
                                if (it.number <= 1) 12 else it.number - 1
                            )
                        )
                    )
                    list.addAll(
                        DateTimeUtils.getCalendarDays(todayDate.year, it)
                    )
                    list.addAll(
                        DateTimeUtils.getCalendarDays(
                            todayDate.year, Month(
                                if (it.number >= 12) 1 else it.number + 1
                            )
                        )
                    )
                    list
                }
            }
        }

        Scaffold { pVal ->
            Column(
                modifier = Modifier.padding(pVal).fillMaxSize(),
            ) {

                if (eventScreenModel.isEventLoading.value) {
                    EventLoadingShimmer()
                } else {
                    if (trendingEventItems.isNotEmpty())
                        Row(
                            modifier = Modifier.padding(12.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Label(
                                size = 18.sp,
                                text = "Trending Events",
                                modifier = Modifier.padding(12.dp).weight(1f),
                                textColor = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            /*TextButton(onClick = {}) {
                                SText("View more")
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }*/
                        }
                    BannerCarouselWidget(trendingEventItems) {
                        navigator.push(EventDetailScreen(it))
                    }
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Label(
                        size = 22.sp,
                        text = "Upcoming Events",
                        modifier = Modifier.padding(12.dp).weight(1f),
                        textColor = MaterialTheme.colorScheme.secondary
                    )
                    /* if (selectedDay.day != todayDate.dayOfMonth && selectedDay.month == todayDate.date.month) {
                         TextButton(onClick = {
                             eventScreenModel.refreshTodayDate()
                         }) {
                             SText("Today")
                         }
                     }*/
                }

                AnimatedCalendar(
                    daysList = daysList,
                    todayDate = todayDate,
                    selectedDay = selectedDay,
                    onDaySelected = { date ->
                        Logger.e(
                            "${date.localDate}",
                            null,
                            tag = "TAG111"
                        )
                        eventScreenModel.updateSelectedDay(date)
                        eventScreenModel.getEventsByDate("${date.localDate}")
                    }
                )

                LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                    if (selectedDateEvents.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier.padding(top = 100.dp).fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Title(
                                    "No events found"
                                )
                            }
                        }
                    }

                    // example
                    /* item {
                         MiniEventItemCard(
                             modifier = Modifier,
                             eventDto = EventDto(
                                 eventTitle = "Independence day",
                                 description = "this is independece day ",
                                 type = "miniEvent",
                                 eventName = "Independence day"
                             ),
                             calendarDate = selectedDay
                         )
                     }*/

                    items(selectedDateEvents) { event ->
                        when (event.type.lowercase()) {
                            "casual", "trending", "normal", "default" -> {
                                EventItemCard(
                                    modifier = Modifier.height(220.dp).padding(12.dp),
                                    eventDto = event,
                                    onBannerClick = {
                                        navigator.push(EventDetailScreen(it))
                                    }
                                )
                            }

                            "miniEvent".lowercase() -> {
                                MiniEventItemCard(
                                    modifier = Modifier.padding(12.dp),
                                    eventDto = event,
                                    calendarDate = selectedDay
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun MiniEventItemCard(
        modifier: Modifier = Modifier,
        eventDto: EventDto,
        calendarDate: CalendarDate
    ) {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Calendar Day Number
                Text(
                    text = calendarDate.day.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Column(modifier = Modifier.weight(1f)) {
                    // Event Title
                    Text(
                        text = eventDto.eventTitle,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    // Event Description if available
                    eventDto.description?.let {
                        if (it.isNotEmpty()) {
                            ExpandableText(
                                text = it,
                                fontSize = 12.sp,
                                style = TextStyle(MaterialTheme.colorScheme.onSurfaceVariant)
                            )
                        }
                    }
                }
            }
        }
    }


    @Composable
    private fun EventLoadingShimmer() {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(30.dp).width(150.dp)
                    .background(CardDefaults.cardColors().containerColor)
                    .shimmerLoadingAnimation()
            )
            /*Box(
                modifier = Modifier.width(100.dp).height(40.dp)
                    .background(CardDefaults.cardColors().containerColor)
                    .shimmerLoadingAnimation()
            )*/
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .fillMaxWidth()
                .height(220.dp)
                .clip(CardDefaults.shape)
                .background(CardDefaults.cardColors().containerColor)
                .shimmerLoadingAnimation()
        ) {}
    }

    @Composable
    fun BannerCarouselWidget(
        banners: List<EventDto>,
        modifier: Modifier = Modifier,
        onBannerClick: (EventDto) -> Unit = {}
    ) {
        val pagerState = rememberPagerState(pageCount = { banners.size })
        if (banners.isNotEmpty()) {
            AutoSwipePagerEffect(pagerState, 4000L)
        }
        Column(modifier) {
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 32.dp),
                pageSpacing = 8.dp,
                verticalAlignment = Alignment.Top,
            ) { page ->
                EventItemCard(
                    modifier = Modifier.height(if (pagerState.currentPage == page) 220.dp else 190.dp)
                        .offset(y = if (pagerState.currentPage == page) (-10).dp else 0.dp)
                        .shadow(
                            20.dp,
                            CardDefaults.shape,
                            ambientColor = MaterialTheme.colorScheme.onBackground,
                            spotColor = MaterialTheme.colorScheme.onBackground
                        ),
                    eventDto = banners[page],
                    onBannerClick = {
                        if (page == pagerState.currentPage) {
                            onBannerClick(it)
                        }
                    }
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun EventItemCard(
        eventDto: EventDto,
        onBannerClick: (EventDto) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Card(
            modifier = modifier
        ) {
            Box(
                contentAlignment = Alignment.BottomStart
            ) {
                BannerWidget(
                    imageUrl = eventDto.eventPic ?: DummyBgImage,
                    contentDescription = eventDto.eventTitle,
                    modifier = Modifier.fillMaxHeight()
                        .clickable { onBannerClick(eventDto) }
                )
                Column(
                    modifier = Modifier.padding(20.dp).fillMaxWidth(),
                ) {
                    Title(
                        text = eventDto.eventTitle ?: "",
                        textColor = Color.White
                    )
                    Text(
                        text = eventDto.description ?: "",
                        color = Color.White.copy(.7f),
                        fontFamily = displayFontFamily(),
                        fontSize = 10.ssp,
                        lineHeight = TextUnit.Unspecified,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }
            }
        }
    }
}