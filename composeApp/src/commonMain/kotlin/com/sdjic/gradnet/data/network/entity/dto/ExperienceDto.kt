package com.sdjic.gradnet.data.network.entity.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExperienceDto(
    @SerialName("id") val id: Long? = 0,
    @SerialName("job_title") val jobTitle: String,
    @SerialName("job_type") val jobType: String? = "",
    @SerialName("company_name") val companyName: String? = "",
    @SerialName("location") val location: String? = "",
    @SerialName("job_description") val jobDescription: String? = "",
    @SerialName("start_date") val startDate: String? = null,
    @SerialName("end_date") val endDate: String? = null
)
