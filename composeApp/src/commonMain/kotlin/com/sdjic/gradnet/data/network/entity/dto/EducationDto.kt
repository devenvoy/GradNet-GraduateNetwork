package com.sdjic.gradnet.data.network.entity.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EducationDto(
    @SerialName("institution") val schoolName: String,
    @SerialName("degree") val degree: String? = "",
    @SerialName("field_of_study") val fieldOfStudy: String? = "",
    @SerialName("location") val location: String? = "",
    @SerialName("description") val description: String? = "",
    @SerialName("start_date") val startDate: String? = null,
    @SerialName("end_date") val endDate: String? = null
)
