package com.sdjic.data.network

import com.sdjic.commons.model.ServerError
import com.sdjic.commons.model.ServerResponse
import com.sdjic.commons.utils.BaseGateway
import com.sdjic.commons.utils.Result
import com.sdjic.domain.model.response.EventResponse
import com.sdjic.domain.repo.EventRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class EventRepositoryImpl(
    private val httpClient: HttpClient
) : EventRepository, BaseGateway(httpClient) {


    override suspend fun getEvents(
        limit: Int,
        offset: Int,
        eventTitle: String?,
        venue: String?,
        startDate: String?,
        endDate: String?
    ): Result<ServerResponse<EventResponse>, ServerError> {
        val response = tryToExecute<ServerResponse<EventResponse>> {
            post(Config.getBaseUrl()+ "/events") {
                headers {
                    append("accept", "application/json")
                    append("Content-Type", "application/json")
                }
                setBody(
                    mapOf(
                        "offset" to offset,
                        "limit" to limit
                    ).also {
                        eventTitle?.let { "event_title" to it }
                        startDate?.let { "start_date" to it }
                        endDate?.let { "end_date" to it }
                        venue?.let { "venue" to it }
                    }
                )
            }
        }
        return response
    }
}