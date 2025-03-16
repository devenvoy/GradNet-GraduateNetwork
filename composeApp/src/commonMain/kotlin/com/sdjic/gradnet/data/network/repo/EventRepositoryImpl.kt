package com.sdjic.gradnet.data.network.repo

import GradNet_GraduateNetwork.composeApp.BuildConfig
import com.sdjic.gradnet.data.network.entity.dto.EventDto
import com.sdjic.gradnet.data.network.entity.response.ServerError
import com.sdjic.gradnet.data.network.entity.response.ServerResponse
import com.sdjic.gradnet.data.network.utils.BaseGateway
import com.sdjic.gradnet.data.network.utils.Result
import com.sdjic.gradnet.domain.repo.EventRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class EventRepositoryImpl(httpClient: HttpClient) : EventRepository, BaseGateway(httpClient) {

    override suspend fun getEvents(eventType: String): Result<ServerResponse<List<EventDto>>, ServerError> {
        return tryToExecute<ServerResponse<List<EventDto>>> {
            post(BuildConfig.BASE_URL + "/events") {
                headers {
                    append("accept", "application/json")
                    append("Content-Type", "application/json")
                }
                setBody(
                    mapOf(
                        "event_type" to eventType.uppercase()
                    )
                )
            }
        }
    }

    override suspend fun getEventsByDate(date: String): Result<ServerResponse<List<EventDto>>, ServerError> {
        return tryToExecute<ServerResponse<List<EventDto>>> {
            post(BuildConfig.BASE_URL + "/events-by-date") {
                headers {
                    append("accept", "application/json")
                    append("Content-Type", "application/json")
                }
                setBody(
                    mapOf(
                        "event_date" to date,
                    )
                )
            }
        }
    }
}