package com.sdjic.gradnet.presentation.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExperienceModel(
    @SerialName("job_title") val title: String = "",
    @SerialName("job_type") val type: String? = null,
    @SerialName("company_name") val company: String? = null,
    @SerialName("location") val location: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("start_date") val startDate: String? = null,
    @SerialName("end_date") val endDate: String? = null,
)