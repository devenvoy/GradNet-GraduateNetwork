package com.sdjic.gradnet.data.network.entity

import com.sdjic.gradnet.data.network.entity.dto.URLDto
import com.sdjic.gradnet.presentation.core.model.EducationModel
import com.sdjic.gradnet.presentation.core.model.ExperienceModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileRequest(
    @SerialName("role") val role: String,
    @SerialName("name") val name: String,
    @SerialName("address") val address: String,
    @SerialName("about_self") val about_self: String,
    @SerialName("languages") val languages: List<String>,
    @SerialName("skills") val skills: List<String>,
    @SerialName("industry_type") val industry_type: String,
    @SerialName("employee") val employee: Int,
    @SerialName("website") val website: String,
    @SerialName("department") val department: String,
    @SerialName("designation") val designation: String,
    val education: List<EducationModel>,
    val experience: List<ExperienceModel>,
    val urls: List<URLDto>
)