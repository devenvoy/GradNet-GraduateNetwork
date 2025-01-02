package com.sdjic.gradnet.presentation.core.model

import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole

sealed interface BaseUser {
    val id: String
    val userId: String
    val profilePic: String?
    val backgroundPic: String?
    val name:String
    val about: String
    val email: String
    val phoneNumber: String
    val showPersonalDetails: Boolean
    val address: String
    val socialUrls: SocialUrls?
    val skills: List<String>?
    val languages: List<String>
    val educations: List<EducationModel>?
    val experiences: List<ExperienceModel>?
    val userRole: UserRole

    data class AlumniUser(
        override val id: String = "",
        override val userId: String = "",
        override val profilePic: String? = null,
        override val backgroundPic: String? = null,
        override val name:String = "",
        override val about: String = "",
        override val email: String = "",
        override val phoneNumber: String = "",
        override val showPersonalDetails: Boolean = true,
        override val address: String = "",
        override val skills: List<String>? = null,
        override val languages: List<String> = emptyList(),
        override val educations: List<EducationModel>? = null,
        override val experiences: List<ExperienceModel>? = null,
        override val socialUrls: SocialUrls = SocialUrls(),
        override val userRole: UserRole = UserRole.Alumni,
    ) : BaseUser

    data class FacultyUser(
        override val id: String = "",
        override val userId: String = "",
        override val profilePic: String? = null,
        override val backgroundPic: String? = null,
        override val name:String = "",
        override val about: String = "",
        override val email: String = "",
        override val phoneNumber: String = "",
        override val showPersonalDetails: Boolean = true,
        override val address: String = "",
        override val skills: List<String>? = null,
        override val languages: List<String> = emptyList(),
        override val educations: List<EducationModel>? = null,
        override val experiences: List<ExperienceModel>? = null,
        override val socialUrls: SocialUrls = SocialUrls(),
        override val userRole: UserRole = UserRole.Faculty,
    ) : BaseUser

    data class OrganizationUser(
        override val id: String = "",
        override val userId: String = "",
        override val profilePic: String? = null,
        override val backgroundPic: String? = null,
        override val name:String = "",
        override val about: String = "",
        override val email: String = "",
        override val phoneNumber: String = "",
        override val showPersonalDetails: Boolean = true,
        override val address: String = "",
        override val skills: List<String> = emptyList(),
        override val languages: List<String> = emptyList(),
        override val educations: List<EducationModel>? = null,
        override val experiences: List<ExperienceModel>? = null,
        override val socialUrls: SocialUrls = SocialUrls(),
        override val userRole: UserRole = UserRole.Organization,
    ) : BaseUser
}


