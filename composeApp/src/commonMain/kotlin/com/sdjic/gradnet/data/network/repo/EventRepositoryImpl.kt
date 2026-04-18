package com.sdjic.gradnet.data.network.repo

import GradNet_GraduateNetwork.composeApp.BuildConfig
import com.sdjic.gradnet.data.network.entity.dto.EventDto
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.utils.BaseGateway
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.domain.repo.EventRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.contentType

class EventRepositoryImpl(httpClient: HttpClient) : EventRepository, BaseGateway(httpClient) {

    override suspend fun getEvents(eventType: String): Result<ServerResponse<List<EventDto>>, ServerError> {
        return tryToExecute<ServerResponse<List<EventDto>>> {
            post(BuildConfig.BASE_URL + "/events/filter") {
                contentType(io.ktor.http.ContentType.Application.Json)
                header("Authorization", "Bearer ${""}")
                parameter("page", "1")
                parameter("perPage", "10")
                setBody(
                    mapOf("eventType" to eventType.uppercase())
                )
            }
        }
    }

    override suspend fun getEventsByDate(date: String): Result<ServerResponse<List<EventDto>>, ServerError> {
        return tryToExecute<ServerResponse<List<EventDto>>> {
            post(BuildConfig.BASE_URL + "/events/by-date") {
                contentType(io.ktor.http.ContentType.Application.Json)
                parameter("page", "1")
                parameter("perPage", "10")
                setBody(
                    mapOf("eventDate" to date)
                )
            }
        }
    }
}