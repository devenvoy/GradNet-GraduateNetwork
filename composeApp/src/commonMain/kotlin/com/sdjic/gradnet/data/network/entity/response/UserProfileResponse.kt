package com.sdjic.gradnet.data.network.entity.response

import com.sdjic.gradnet.data.network.entity.dto.EducationDto
import com.sdjic.gradnet.data.network.entity.dto.ExperienceDto
import com.sdjic.gradnet.data.network.entity.dto.URLDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(
    @SerialName("id") var id: String,
    @SerialName("name") var name: String = "",
    @SerialName("email") var email: String = "",
    @SerialName("phone_no") var phoneNo: Long? = null,
    @SerialName("verify_id") var verifyId: Int? = null,
    @SerialName("profile_pic") var profilePic: String? = "",
    @SerialName("background_pic") var backgroundPic: String? = "",
    @SerialName("verified") var verified: Boolean = false,
    @SerialName("plus_member") var plusMember: Boolean = false,
    @SerialName("about_self") var aboutSelf: String? = "",

    @SerialName("college_name") var collegeName: String? = "",
    @SerialName("course") var course: String? = "",
    @SerialName("role") var role: String,

    @SerialName("address") var address: String? = "",
    @SerialName("dob") var dob: String? = "",
    @SerialName("gender") var gender: String? = "",
    @SerialName("languages") var languages: List<String>? = emptyList(),
    @SerialName("skills") var skills: List<String>? = emptyList(),

    @SerialName("industry_type") var industryType: String? = null,
    @SerialName("employee") var employee: String? = null,
    @SerialName("website") var website: String? = null,
    @SerialName("department") var department: String? = null,
    @SerialName("designation") var designation: String? = null,

    @SerialName("education") var education: List<EducationDto> = emptyList(),
    @SerialName("experience") var experience: List<ExperienceDto> = emptyList(),
    @SerialName("urls") var urls: List<URLDto> = emptyList(),

    @SerialName("is_active") var isActive: Boolean = false,
    @SerialName("created_at") var createdAt: String = "",
    @SerialName("updated_at") var updatedAt: String = ""
) {
    @SerialName("access_token")
    val accessToken: String = ""
}
