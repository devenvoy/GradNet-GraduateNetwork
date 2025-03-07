package com.sdjic.gradnet.data.network.utils

import com.sdjic.gradnet.data.local.entity.EducationTable
import com.sdjic.gradnet.data.local.entity.ExperienceTable
import com.sdjic.gradnet.data.local.entity.UrlTable
import com.sdjic.gradnet.data.network.entity.dto.EducationDto
import com.sdjic.gradnet.data.network.entity.dto.ExperienceDto
import com.sdjic.gradnet.data.network.entity.dto.URLDto
import com.sdjic.gradnet.data.network.entity.response.UserProfileResponse
import com.sdjic.gradnet.presentation.core.model.EducationModel
import com.sdjic.gradnet.presentation.core.model.ExperienceModel
import com.sdjic.gradnet.presentation.core.model.SocialUrls
import com.sdjic.gradnet.presentation.core.model.UserProfile


fun EducationDto.toEducationModel(): EducationModel {
    return EducationModel(
        schoolName = this.schoolName,
        degree = this.degree,
        field = this.fieldOfStudy,
        location = this.location,
        description = this.description,
        startDate = this.startDate,
        endDate = this.endDate
    )
}

fun ExperienceDto.toExperienceModel(): ExperienceModel {
    return ExperienceModel(
        title = this.jobTitle,
        type = this.jobType?.ifEmpty { null },
        company = this.companyName?.ifEmpty { null },
        location = this.location?.ifEmpty { null },
        description = this.jobDescription?.ifEmpty { null },
        startDate = this.startDate?.ifEmpty { null },
        endDate = this.endDate?.ifEmpty { null }
    )
}


fun List<URLDto>.toSocialUrls(): SocialUrls {
    val linkedIn = this.find { it.type.lowercase().equals("linkedinUrl", ignoreCase = true) }?.url
    val github = this.find { it.type.lowercase().equals("githubUrl", ignoreCase = true) }?.url
    val twitter = this.find { it.type.lowercase().equals("twitterUrl", ignoreCase = true) }?.url
    val otherUrls = this.filterNot {
        it.type.equals("linkedinUrl", ignoreCase = true) ||
                it.type.equals("twitterUrl", ignoreCase = true) ||
                it.type.equals("githubUrl", ignoreCase = true)
    }.map { it.url ?: "" }.filter { it.isEmpty() }

    return SocialUrls(
        linkedIn = linkedIn,
        github = github,
        twitter = twitter,
        otherUrls = otherUrls
    )
}

fun UserProfileResponse.toUserProfile(): UserProfile {
    return UserProfile(
        userId = this.id,
        userName = this.name,
        email = this.email,
        verificationId = (this.verifyId ?: "").toString(),
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
        experiences = this.experience.map { it.toExperienceModel() }.takeIf { it.isNotEmpty() },
        website = this.website,
        industryType = this.industryType,
        department = this.department,
        designation = this.designation,
        employee = this.employee,
    )
}

fun UrlTable.toUrlDto(): URLDto {
    return URLDto(type = type, url = url)
}

fun URLDto.toUrlTable(): UrlTable {
    return UrlTable(0, type, url ?: "")
}

fun EducationDto.toEducationTable(): EducationTable {
    return EducationTable(
        schoolName = schoolName,
        degree = degree,
        field = fieldOfStudy,
        location = location,
        description = description,
        startDate = startDate,
        endDate = endDate
    )
}

fun ExperienceDto.toExperienceTable(): ExperienceTable {
    return ExperienceTable(
        title = jobTitle,
        type = jobType,
        company = companyName,
        location = location,
        description = jobDescription,
        startDate = startDate,
        endDate = endDate
    )
}

fun EducationTable.toEducationModel(): EducationModel {
    return EducationModel(
        schoolName = this.schoolName,
        degree = this.degree,
        field = this.field,
        location = this.location,
        description = this.description,
        startDate = this.startDate,
        endDate = this.endDate
    )
}

fun ExperienceTable.toExperienceModel(): ExperienceModel {
    return ExperienceModel(
        title = this.title,
        type = this.type,
        company = this.company,
        location = this.location,
        description = this.description,
        startDate = this.startDate,
        endDate = this.endDate
    )
}

