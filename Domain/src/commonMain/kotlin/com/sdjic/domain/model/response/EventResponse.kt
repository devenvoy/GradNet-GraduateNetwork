package com.sdjic.domain.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventResponse(
    @SerialName("has_more") val hasMore: Boolean,
    @SerialName("limit") val limit: Int,
    @SerialName("offset") val offset: Int,
    @SerialName("records") val eventDtos: List<EventDto>,
    @SerialName("total_records") val totalRecords: Int
)