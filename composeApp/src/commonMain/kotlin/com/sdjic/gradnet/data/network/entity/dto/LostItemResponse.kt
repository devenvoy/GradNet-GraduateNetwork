package com.sdjic.gradnet.data.network.entity.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LostItemResponse(
    @SerialName("entries") val entries: List<LostItemDto>,
    @SerialName("page") val page: Int,
    @SerialName("per_page") val perPage: Int
)