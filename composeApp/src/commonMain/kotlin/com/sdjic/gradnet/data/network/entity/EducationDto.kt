package com.sdjic.gradnet.data.network.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EducationDto(
    @SerialName("id") val id: Long,
    @SerialName("user_id") val userId: String,
    @SerialName("school_name") val schoolName: String,
    @SerialName("degree") val degree: String,
    @SerialName("field_of_study") val fieldOfStudy: String?,
    @SerialName("location") val location: String?,
    @SerialName("description") val description: String?,
    @SerialName("start_date") val startDate: String?,
    @SerialName("end_date") val endDate: String?
)
