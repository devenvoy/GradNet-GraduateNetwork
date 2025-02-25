package com.sdjic.gradnet.data.network.entity

import com.sdjic.gradnet.data.network.entity.dto.URLDto
import com.sdjic.gradnet.presentation.core.model.EducationModel
import com.sdjic.gradnet.presentation.core.model.ExperienceModel
import com.sdjic.gradnet.presentation.core.model.SocialUrls
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileRequest(
    val role: String,
    val name: String,
    val address: String,
    val about_self: String,
    val languages: List<String>,
    val skills: List<String>,
    val industry_type: String,
    val employee: Int,
    val website: String,
    val department: String,
    val designation: String,
    val education: List<EducationModel>,
    val experience: List<ExperienceModel>,
    val urls: List<URLDto>
)