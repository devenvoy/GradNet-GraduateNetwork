package com.sdjic.gradnet.data.network.entity.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LostItemDto(
    @SerialName("created_at") val createdAt: String,
    @SerialName("description") val description: String,
    @SerialName("id") val id: String,
    @SerialName("photos") val photos: List<String>,
    @SerialName("user_id") val userId: String
)