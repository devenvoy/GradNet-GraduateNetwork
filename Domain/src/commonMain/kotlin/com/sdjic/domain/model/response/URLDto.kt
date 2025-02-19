package com.sdjic.domain.model.response
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class URLDto(
    @SerialName("url_type") val type: String,
    @SerialName("url") val url: String
)