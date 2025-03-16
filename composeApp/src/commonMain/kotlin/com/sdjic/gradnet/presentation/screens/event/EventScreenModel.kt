package com.sdjic.gradnet.presentation.screens.event

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.entity.dto.EventDto
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.domain.repo.EventRepository
import com.sdjic.gradnet.presentation.core.model.CalendarDate
import com.sdjic.gradnet.presentation.helper.DateTimeUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.number

class EventScreenModel(
    private val eventRepository: EventRepository
) : ScreenModel {

    private val _trendingEventList = MutableStateFlow<List<EventDto>>(emptyList())
    val trendingEventList = _trendingEventList.asStateFlow()

    private val _selectedDateEventsList = MutableStateFlow<List<EventDto>>(emptyList())
    val selectedDateEventsList = _selectedDateEventsList.asStateFlow()

    val isEventLoading = mutableStateOf(false)

    private val _todayDate = MutableStateFlow(DateTimeUtils.now())
    val todayDate = _todayDate

    private val _selectedDay = MutableStateFlow(
        CalendarDate(
            day = _todayDate.value.dayOfMonth,
            dayOfWeek = _todayDate.value.date.dayOfWeek.name,
            month = _todayDate.value.date.month,
            localDate = _todayDate.value.date
        )
    )
    val selectedDay: StateFlow<CalendarDate> = _selectedDay


    init {
        getEvents()
        refreshTodayDate()
    }


    fun refreshTodayDate() {
        val newDate = _todayDate.value
        _selectedDay.update {
            CalendarDate(
                day = newDate.dayOfMonth,
                dayOfWeek = newDate.date.dayOfWeek.name,
                month = newDate.date.month,
                localDate = newDate.date
            )
        }
    }

    fun updateSelectedDay(date: CalendarDate) {
        _selectedDay.update { date }
    }

    private fun getEvents() = screenModelScope.launch {
        isEventLoading.value = true
        eventRepository.getEvents(eventType = "trending").onSuccess { r ->
            isEventLoading.value = false
            _trendingEventList.update { r.value ?: it }
        }.onError {
            isEventLoading.value = false
        }
    }

    fun getEventsByDate(date: String) {
        screenModelScope.launch {
            screenModelScope.launch {
                val result = eventRepository.getEventsByDate(date)
                result.onSuccess { r ->
                    _selectedDateEventsList.update { r.value ?: emptyList() }
                }.onError { e ->
                    _selectedDateEventsList.update { emptyList() }
                }
            }
        }
    }
}