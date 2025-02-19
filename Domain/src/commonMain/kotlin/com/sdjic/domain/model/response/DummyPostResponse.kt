package com.sdjic.domain.model.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DummyPostResponse(
    @SerialName("limit") val limit: Int?,
    @SerialName("posts") val posts: List<Post?>?,
    @SerialName("skip") val skip: Int?,
    @SerialName("total") val total: Int?
)