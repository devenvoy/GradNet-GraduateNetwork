package com.sdjic.gradnet.domain.repo

import com.sdjic.gradnet.data.network.entity.response.EventResponse
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.utils.Result

interface EventRepository {

    suspend fun getEvents(
        limit: Int,
        offset: Int = 0,
        eventTitle: String? = null,
        venue : String? = null,
        startDate: String? = null,
        endDate: String? = null
    ) : Result<ServerResponse<EventResponse>, ServerError>
}