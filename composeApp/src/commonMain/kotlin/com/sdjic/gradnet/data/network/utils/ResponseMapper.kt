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
    val displayName = listOfNotNull(this.firstName, this.lastName)
        .joinToString(" ")
        .takeIf { it.isNotEmpty() }
        ?: this.displayName
        ?: ""

    val experienceList = this.experience?.map { expMap ->
        ExperienceModel(
            title = expMap["job_title"] ?: expMap["title"] ?: "",
            type = expMap["job_type"]?.ifEmpty { null },
            company = expMap["company_name"]?.ifEmpty { null },
            location = expMap["location"]?.ifEmpty { null },
            description = expMap["job_description"]?.ifEmpty { null },
            startDate = expMap["start_date"]?.ifEmpty { null },
            endDate = expMap["end_date"]?.ifEmpty { null },
        )
    } ?: emptyList()

    return UserProfile(
        userId = this.userId,
        userName = displayName,
        email = this.email ?: "",
        verificationId = "",
        isVerified = false,
        isPlusMember = false,
        isActive = true,
        isDeleted = false,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        dob = null,
        gender = null,
        skills = this.skills,
        languages = this.languages,
        educations = this.education?.map { it.toEducationModel() } ?: emptyList(),
        name = displayName,
        backgroundPic = this.avatarUrl,
        profilePic = this.avatarUrl,
        about = this.aboutSelf ?: "",
        phoneNumber = "",
        isPrivate = this.isPrivate,
        address = null,
        socialUrls = this.urls.takeIf { it?.isNotEmpty() == true }?.toSocialUrls(),
        experiences = experienceList,
        website = this.website,
        userRole = UserRole.Alumni,
        industryType = this.industryType,
        department = this.department,
        designation = this.designation,
        employee = this.employee,
        course = null
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
        postId = it.id,
        userId = it.userId,
        userName = it.userName.orEmpty(),
        userImage = it.userAvatar.orEmpty(),
        userRole = UserRole.getUserRole(it.userRole ?: "") ?: UserRole.Alumni,
        content = it.description ?: "",
        likesCount = it.likeCount,
        liked = it.isLiked,
        images = it.images.orEmpty(),
        location = it.location.orEmpty(),
        createdAt = it.createdAt
    )
}


fun jobDtoToJob(jobDto: JobDto?) = jobDto?.let {
    Job(
        id = it.id,
        title = it.jobTitle.orEmpty(),
        company = it.companyName.orEmpty(),
        jobType = it.workMode?.lowercase()?.replaceFirstChar { char -> char.uppercase() },
        location = it.jobLocation.orEmpty(),
        description = it.jobOverview.orEmpty(),
        salary = it.salary,
        requirements = it.requirements.orEmpty(),
        benefits = it.benefits.orEmpty(),
        postedDate = it.createdAt,
        applyLink = it.applyLink.orEmpty(),
        companyLogo = it.companyLogo,
        category = "",
        skills = it.skills.orEmpty(),
        isSaved = it.isSaved
    )
}