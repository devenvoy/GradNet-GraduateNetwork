package com.sdjic.gradnet.data.network.entity

import com.sdjic.gradnet.data.network.entity.dto.EducationDto
import com.sdjic.gradnet.data.network.entity.dto.ExperienceDto
import com.sdjic.gradnet.data.network.entity.dto.URLDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(

    @SerialName("id") var id: String = "",
    @SerialName("userName") var userName: String = "",
    @SerialName("email") var email: String = "",
    @SerialName("userId") var userId: String = "",
    @SerialName("verificationId") var verificationId: String = "",
    @SerialName("userRole") var userRole: String = "",
    @SerialName("isVerified") var isVerified: Boolean = false,
    @SerialName("isPlusMember") var isPlusMember: Boolean = false,
    @SerialName("isActive") var isActive: Boolean = false,
    @SerialName("isDeleted") var isDeleted: Boolean = false,
    @SerialName("createdAt") var createdAt: String = "",
    @SerialName("updatedAt") var updatedAt: String = "",

    @SerialName("name") var name: String = "",
    @SerialName("backgroundPic") var backgroundPic: String? = null,
    @SerialName("profilePic") var profilePic: String? = null,
    @SerialName("about") var about: String = "",
    @SerialName("phoneNumber") var phoneNumber: String = "",
    @SerialName("showPersonalDetails") var showPersonalDetails: Boolean = false,
    @SerialName("address") var address: String = "",
    @SerialName("socialUrls") var socialUrls: List<URLDto> = emptyList(),
    @SerialName("experiences") var experiences: List<ExperienceDto> = emptyList(),

    @SerialName("dob") var dob: String? = null,
    @SerialName("gender") var gender: String? = null,
    @SerialName("languages") var languages: List<String>? = null,
    @SerialName("skills") var skills: List<String>? = null,
    @SerialName("educations") var educations: List<EducationDto>? = null,
)