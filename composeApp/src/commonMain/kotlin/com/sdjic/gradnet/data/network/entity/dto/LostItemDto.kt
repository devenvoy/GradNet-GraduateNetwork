package com.sdjic.gradnet.data.network.entity.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LostItemDto(
    @SerialName("id") val id: String,
    @SerialName("description") val description: String? = null,
    @SerialName("images") val images: List<String>? = null,
    @SerialName("userId") val userId: String,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String
)