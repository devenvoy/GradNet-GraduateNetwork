package com.sdjic.domain.repo

import com.sdjic.commons.model.ServerError
import com.sdjic.commons.model.ServerResponse
import com.sdjic.commons.utils.Result
import com.sdjic.domain.model.response.EventResponse

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