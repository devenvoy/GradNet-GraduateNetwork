package com.sdjic.gradnet.presentation.screens.event

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.entity.dto.EventDto
import com.sdjic.gradnet.domain.repo.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventScreenModel(
    private val eventRepository: EventRepository
) : ScreenModel {

    private val _eventList = MutableStateFlow<List<EventDto?>>(emptyList())
    val eventList = _eventList.asStateFlow()

    init {
        getEvents()
    }

    fun getEvents() = screenModelScope.launch {
        val result = eventRepository.getEvents(
            limit = 10,
            offset = 0,
            eventTitle = null,
            venue = null,
            startDate = null,
            endDate = null
        )
        _eventList.update {
            result?.value?.eventDtos ?: it
        }
    }
}