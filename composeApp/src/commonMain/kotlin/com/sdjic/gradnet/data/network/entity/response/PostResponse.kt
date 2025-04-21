package com.sdjic.gradnet.data.network.entity.response


import com.sdjic.gradnet.data.network.entity.dto.PostDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    @SerialName("page") val page: Int,
    @SerialName("per_page") val perPage: Int,
    @SerialName("posts") val postDtos: List<PostDto>
)