package com.sdjic.gradnet.data.network.entity.response

import com.sdjic.gradnet.data.network.entity.dto.EducationDto
import com.sdjic.gradnet.data.network.entity.dto.ExperienceDto
import com.sdjic.gradnet.data.network.entity.dto.URLDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(
    @SerialName("id") val id: String,
    @SerialName("userId") val userId: String,
    @SerialName("firstName") val firstName: String? = null,
    @SerialName("lastName") val lastName: String? = null,
    @SerialName("displayName") val displayName: String? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("avatarUrl") val avatarUrl: String? = null,
    @SerialName("aboutSelf") val aboutSelf: String? = null,
    @SerialName("isPrivate") val isPrivate: Boolean = false,
    @SerialName("languages") val languages: List<String>? = null,
    @SerialName("skills") val skills: List<String>? = null,
    @SerialName("industryType") val industryType: String? = null,
    @SerialName("employee") val employee: String? = null,
    @SerialName("website") val website: String? = null,
    @SerialName("department") val department: String? = null,
    @SerialName("designation") val designation: String? = null,
    @SerialName("education") val education: List<EducationDto>? = null,
    @SerialName("experience") val experience: List<Map<String, String>>? = null,
    @SerialName("urls") val urls: List<URLDto>? = null,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String
)
