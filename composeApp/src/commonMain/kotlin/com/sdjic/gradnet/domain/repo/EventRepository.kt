package com.sdjic.gradnet.domain.repo

import com.sdjic.gradnet.data.network.entity.dto.EventDto
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.utils.Result

interface EventRepository {

    suspend fun getEvents(
        eventType:String
    ): Result<ServerResponse<List<EventDto>>, ServerError>

    suspend fun getEventsByDate(
        date: String
    ): Result<ServerResponse<List<EventDto>>, ServerError>

}