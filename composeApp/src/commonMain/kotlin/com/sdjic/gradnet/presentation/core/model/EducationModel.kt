package com.sdjic.gradnet.presentation.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EducationModel(
    @SerialName("institution") val schoolName: String = "",
    @SerialName("degree") val degree: String? = null,
    @SerialName("field_of_study") val field: String? = null,
    @SerialName("location") val location: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("start_year") val startDate: String? = null,
    @SerialName("end_year") val endDate: String? = null,
)
