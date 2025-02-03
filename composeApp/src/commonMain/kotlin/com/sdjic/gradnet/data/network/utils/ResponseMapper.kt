package com.sdjic.gradnet.data.network.utils

import com.sdjic.gradnet.data.network.entity.dto.EducationDto
import com.sdjic.gradnet.data.network.entity.dto.ExperienceDto
import com.sdjic.gradnet.data.network.entity.dto.URLDto
import com.sdjic.gradnet.data.network.entity.response.UserProfileResponse
import com.sdjic.gradnet.presentation.core.model.EducationModel
import com.sdjic.gradnet.presentation.core.model.ExperienceModel
import com.sdjic.gradnet.presentation.core.model.SocialUrls
import com.sdjic.gradnet.presentation.core.model.UserProfile
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole


fun EducationDto.toEducationModel(): EducationModel {
    return EducationModel(
        id = this.id.toInt(),
        schoolName = this.schoolName,
        degree = this.degree.ifEmpty { null },
        field = this.fieldOfStudy?.ifEmpty { null },
        location = this.location?.ifEmpty { null },
        description = this.description?.ifEmpty { null },
        startDate = this.startDate?.ifEmpty { null },
        endDate = this.endDate?.ifEmpty { null }
    )
}

fun ExperienceDto.toExperienceModel(): ExperienceModel {
    return ExperienceModel(
        id = this.id.toInt(),
        title = this.jobTitle,
        type = this.jobType?.ifEmpty { null },
        company = this.companyName?.ifEmpty { null },
        location = this.location?.ifEmpty { null },
        description = this.jobDescription?.ifEmpty { null },
        startDate = this.startDate?.ifEmpty { null },
        endDate = this.endDate?.ifEmpty { null }
    )
}


fun UserProfileResponse.toUserProfile(): UserProfile {
    return UserProfile(
        id = this.id,
        userName = this.name,
        email = this.email,
        userId = this.id,
        verificationId = this.verifyId ?: "",
        userRole = UserRole.getUserRole(this.role),
        isVerified = this.verified,
        isPlusMember = this.plusMember,
        isActive = this.isActive,
        isDeleted = false,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        dob = this.dob,
        gender = this.gender,
        skills = this.skills,
        languages = this.languages,
        educations = this.education.map { it.toEducationModel() }.takeIf { it.isNotEmpty() },
        name = this.name,
        backgroundPic = this.backgroundPic,
        profilePic = this.profilePic,
        about = this.aboutSelf ?: "",
        phoneNumber = this.phoneNo.toString(),
        showPersonalDetails = false,
        address = this.address,
        socialUrls = this.urls.takeIf { it.isNotEmpty() }?.toSocialUrls(),
        experiences = this.experience.map { it.toExperienceModel() }.takeIf { it.isNotEmpty() }
    )
}


fun List<URLDto>.toSocialUrls(): SocialUrls {
    val linkedIn = this.find { it.type.equals("linkedin", ignoreCase = true) }?.url
    val github = this.find { it.type.equals("github", ignoreCase = true) }?.url
    val twitter = this.find { it.type.equals("twitter", ignoreCase = true) }?.url
    val otherUrls = this.filterNot {
        it.type.equals("linkedin", ignoreCase = true) ||
                it.type.equals("github", ignoreCase = true) ||
                it.type.equals("twitter", ignoreCase = true)
    }.map { it.url }

    return SocialUrls(
        linkedIn = linkedIn,
        github = github,
        twitter = twitter,
        otherUrls = otherUrls
    )
}
