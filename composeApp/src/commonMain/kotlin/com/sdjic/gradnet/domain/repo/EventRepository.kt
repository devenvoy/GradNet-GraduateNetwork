package com.sdjic.gradnet.domain.repo

import com.sdjic.gradnet.data.network.entity.response.EventResponse
import com.sdjic.gradnet.data.network.entity.response.ServerResponse

interface EventRepository {

    suspend fun getEvents(
        limit: Int,
        offset: Int = 0,
        eventTitle: String? = null,
        venue : String? = null,
        startDate: String? = null,
        endDate: String? = null
    ) : ServerResponse<EventResponse>?
}