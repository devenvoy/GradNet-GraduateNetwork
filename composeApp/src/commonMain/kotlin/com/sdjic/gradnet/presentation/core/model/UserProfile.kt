package com.sdjic.gradnet.presentation.core.model

import com.sdjic.commons.model.UserRole
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicState
import com.sdjic.gradnet.presentation.screens.accountSetup.education.EducationState
import com.sdjic.gradnet.presentation.screens.accountSetup.profession.ProfessionState

data class UserProfile(

    // base user
    val id: String = "",
    val userName: String = "",
    val email: String = "",
    val userId: String = "",
    val verificationId : String = "",
    val userRole: UserRole = UserRole.Alumni,
    val isVerified : Boolean = false,
    val isPlusMember : Boolean = false,
    val isActive : Boolean = true,
    val isDeleted : Boolean = false,
    val createdAt : String? = null,
    val updatedAt : String? = null,

    // same for alumni and faculty
    val dob : String? = null,
    val gender : String? = null,
    val skills: List<String>? = null,
    val languages: List<String>? = null,
    val educations: List<EducationModel>? = null,

    // same for all user role
    val name : String = "",
    val backgroundPic: String? = null,
    val profilePic: String? = null,
    val about: String = "",
    val phoneNumber: String = "",
    val showPersonalDetails: Boolean = false,
    val address: String? = null,
    val socialUrls: SocialUrls? = null,
    val experiences: List<ExperienceModel>? = null,
)

fun UserProfile.toBasicState(): BasicState {
    return BasicState(
        nameField = this.name,
        aboutField = this.about,
        addressField = this.address ?: "",
        profileImageUrl = this.profilePic,
        backGroundImageUrl = this.backgroundPic,
        verificationField = this.verificationId,
        otpEmailField = "",
        otpField = "",
        showOtpBottomSheet = false,
        openBackGroundImagePicker = false,
        openProfileImagePicker = false
    )
}


fun UserProfile.toEducationState(): EducationState {
    return EducationState(
        skills = this.skills ?: emptyList(),
        languages = this.languages ?: emptyList(),
        eductionList = this.educations ?: emptyList(),
        showEducationBottomSheet = false,
        showLanguageDialog = false,
        showSkillDialog = false
    )
}

fun UserProfile.toProfessionState(): ProfessionState {
    return ProfessionState(
        linkedinUrl = this.socialUrls?.linkedIn ?: "",
        githubUrl = this.socialUrls?.github ?: "",
        twitterUrl = this.socialUrls?.twitter ?: "",
        otherUrls = this.socialUrls?.otherUrls ?: emptyList(),
        experienceList = this.experiences ?: emptyList()
    )
}

