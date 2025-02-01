package com.sdjic.gradnet.data.network.entity.response


import com.sdjic.gradnet.data.network.entity.dto.EventDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventResponse(
    @SerialName("has_more")
    val hasMore: Boolean,
    @SerialName("limit")
    val limit: Int,
    @SerialName("offset")
    val offset: Int,
    @SerialName("records")
    val eventDtos: List<EventDto?>,
    @SerialName("total_records")
    val totalRecords: Int
)