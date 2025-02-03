package com.sdjic.gradnet.data.network.entity.dto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class URLDto(
    @SerialName("url_type") val type: String,
    @SerialName("url") val url: String
)