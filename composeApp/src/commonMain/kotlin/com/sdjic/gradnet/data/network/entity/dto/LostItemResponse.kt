package com.sdjic.gradnet.data.network.entity.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LostItemResponse(
    @SerialName("current_page") val page: Int,
    @SerialName("per_page") val perPage: Int,
    @SerialName("total_count") val totalCount: Int,
    @SerialName("data") val entries: List<LostItemDto>
)