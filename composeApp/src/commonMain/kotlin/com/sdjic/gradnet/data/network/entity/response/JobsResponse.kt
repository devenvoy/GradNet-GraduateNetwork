package com.sdjic.gradnet.data.network.entity.response

import com.sdjic.gradnet.data.network.entity.dto.JobDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JobsResponse(
    @SerialName("page") val page: Int,
    @SerialName("per_page") val perPage: Int,
    @SerialName("jobs") val jobDtos: List<JobDto>
)