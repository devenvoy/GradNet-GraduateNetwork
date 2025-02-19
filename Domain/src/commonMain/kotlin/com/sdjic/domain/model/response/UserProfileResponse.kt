package com.sdjic.domain.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(
    @SerialName("about_self") var aboutSelf: String? = "",
    @SerialName("address") var address: String? = "",
    @SerialName("background_pic") var backgroundPic: String? = "",
    @SerialName("created_at") var createdAt: String = "",
    @SerialName("department") var department: String? = null,
    @SerialName("designation") var designation: String? = null,
    @SerialName("dob") var dob: String? = "",
    @SerialName("education") var education: List<EducationDto> = emptyList(),
    @SerialName("email") var email: String = "",
    @SerialName("employee") var employee: String? = null,
    @SerialName("experience") var experience: List<ExperienceDto> = emptyList(),
    @SerialName("gender") var gender: String? = "",
    @SerialName("id") var id: String = "",
    @SerialName("industry_type") var industryType: String? = null,
    @SerialName("is_active") var isActive: Boolean = false,
    @SerialName("languages") var languages: List<String>? = emptyList(),
    @SerialName("name") var name: String = "",
    @SerialName("phone_no") var phoneNo: Long? = null,
    @SerialName("plus_member") var plusMember: Boolean = false,
    @SerialName("profile_pic") var profilePic: String? = "",
    @SerialName("role") var role: String = "",
    @SerialName("skills") var skills: List<String>? = listOf(),
    @SerialName("updated_at") var updatedAt: String = "",
    @SerialName("urls") var urls: List<URLDto> = listOf(),
    @SerialName("verified") var verified: Boolean = false,
    @SerialName("verify_id") var verifyId: Int? = null,
    @SerialName("website") var website: String? = null
) {
    @SerialName("access_token")
    val accessToken: String = ""
}
