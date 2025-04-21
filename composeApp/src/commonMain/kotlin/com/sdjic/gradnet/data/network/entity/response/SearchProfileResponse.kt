package com.sdjic.gradnet.data.network.entity.response

import com.sdjic.gradnet.data.network.entity.dto.UserDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchProfileResponse(
    @SerialName("users") var users: List<UserDto> = arrayListOf(),
    @SerialName("page") var page: Int,
    @SerialName("per_page") var perPage: Int,
    @SerialName("total_count") var totalCount: Int
)
