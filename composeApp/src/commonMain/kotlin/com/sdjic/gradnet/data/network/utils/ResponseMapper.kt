package com.sdjic.gradnet.data.network.utils

import com.sdjic.gradnet.data.local.entity.EducationTable
import com.sdjic.gradnet.data.local.entity.ExperienceTable
import com.sdjic.gradnet.data.local.entity.UrlTable
import com.sdjic.gradnet.data.network.entity.dto.EducationDto
import com.sdjic.gradnet.data.network.entity.dto.ExperienceDto
import com.sdjic.gradnet.data.network.entity.dto.JobDto
import com.sdjic.gradnet.data.network.entity.dto.PostDto
import com.sdjic.gradnet.data.network.entity.dto.URLDto
import com.sdjic.gradnet.data.network.entity.response.UserProfileResponse
import com.sdjic.gradnet.presentation.core.model.EducationModel
import com.sdjic.gradnet.presentation.core.model.ExperienceModel
import com.sdjic.gradnet.presentation.core.model.Job
import com.sdjic.gradnet.presentation.core.model.Post
import com.sdjic.gradnet.presentation.core.model.SocialUrls
import com.sdjic.gradnet.presentation.core.model.UserProfile
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole


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
        educations = this.education.map { it.toEducationModel() },
        name = this.name,
        backgroundPic = this.backgroundPic,
        profilePic = this.profilePic,
        about = this.aboutSelf ?: "",
        phoneNumber = this.phoneNo.toString(),
        isPrivate = this.isPrivate?.not() ?: false,
        address = this.address,
        socialUrls = this.urls.takeIf { it.isNotEmpty() }?.toSocialUrls(),
        experiences = this.experience.map { it.toExperienceModel() },
        website = this.website,
        userRole = UserRole.getUserRole(this.role) ?: UserRole.Alumni,
        industryType = this.industryType,
        department = this.department,
        designation = this.designation,
        employee = this.employee,
        course = this.course
    )
}

fun UrlTable.toUrlDto(): URLDto {
    return URLDto(type = type, url = url)
}

fun URLDto.toUrlTable(): UrlTable {
    return UrlTable(0, type, url)
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


fun postDtoToPost(postDto: PostDto?) = postDto?.let {
    Post(
        postId = it.postId,
        userId = it.userId.orEmpty(),
        userName = it.userName.orEmpty(),
        userImage = it.userProfilePic.orEmpty(),
        userRole = UserRole.getUserRole(it.userRole ?: "") ?: UserRole.Alumni,
        content = it.description,
        likesCount = it.likes,
        liked = it.isLiked,
        images = it.photos?.filterNotNull() ?: emptyList(),
        location = it.location.orEmpty(),
        createdAt = it.createdAt
    )
}


fun jobDtoToJob(jobDto: JobDto?) = jobDto?.let {
    Job(
        id = it.jobId.toString(),
        title = it.jobTitle.orEmpty(),
        company = it.companyName.orEmpty(),
        jobType = it.workMode?.lowercase()?.replaceFirstChar { char -> char.uppercase() },
        location = it.jobLocation.orEmpty(),
        description = it.jobOverview.orEmpty(),
        salary = it.salary,
        requirements = it.requirements.orEmpty(),
        benefits = it.benefits.orEmpty(),
        postedDate = it.createdAt.toString(),
        applyLink = it.applylink.orEmpty(),
        companyLogo = it.companyLogo,
        category = it.industry.orEmpty(),
        skills = it.skills.orEmpty(),
        isSaved = it.isSaved ?: false
    )
}