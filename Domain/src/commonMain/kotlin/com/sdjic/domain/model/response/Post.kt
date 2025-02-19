package com.sdjic.domain.model.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    @SerialName("body") val body: String?,
    @SerialName("id") val id: Int?,
    @SerialName("reactions") val reactions: Reactions?,
    @SerialName("tags") val tags: List<String?>?,
    @SerialName("title") val title: String?,
    @SerialName("userId") val userId: Int?,
    @SerialName("views") val views: Int?
)