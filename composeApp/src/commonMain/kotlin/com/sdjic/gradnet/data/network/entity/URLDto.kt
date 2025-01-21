package com.sdjic.gradnet.data.network.entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class URLDto(
    @SerialName("id") val id: Long,
    @SerialName("user_id") val userId: String,
    @SerialName("type") val type: String,
    @SerialName("url") val url: String
)