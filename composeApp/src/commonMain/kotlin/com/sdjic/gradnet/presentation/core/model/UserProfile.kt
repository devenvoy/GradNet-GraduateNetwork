package com.sdjic.gradnet.presentation.core.model

import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicState
import com.sdjic.gradnet.presentation.screens.accountSetup.education.EducationState
import com.sdjic.gradnet.presentation.screens.accountSetup.profession.ProfessionState
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole

data class UserProfile(
    val id: String = "",
    val userId: String = "",
    val profilePic: String? = null,
    val backgroundPic: String? = null,
    val name: String = "",
    val about: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val showPersonalDetails: Boolean = false,
    val address: String? = null,
    val socialUrls: SocialUrls? = null,
    val skills: List<String>? = null,
    val languages: List<String> = emptyList(),
    val educations: List<EducationModel>? = null,
    val experiences: List<ExperienceModel>? = null,
    val userRole: UserRole = UserRole.Alumni
)

fun UserProfile.toBasicState(): BasicState {
    return BasicState(
        nameField = this.name,
        aboutField = this.about,
        addressField = this.address ?: "",
        backgroundImage = null, // Convert `backgroundPic` if needed
        profileImage = null,    // Convert `profilePic` if needed
        verificationField = "", // Assuming email is used for verification
        otpEmailField = this.email,
        otpField = "",
        showOtpBottomSheet = false,
        openBackGroundImagePicker = false,
        openProfileImagePicker = false
    )
}


fun UserProfile.toEducationState(): EducationState {
    return EducationState(
        skills = this.skills ?: emptyList(),
        languages = this.languages,
        eductionList = this.educations ?: emptyList(),
        showEducationBottomSheet = false, // Default value as it depends on UI actions
        showLanguageDialog = false,      // Default value
        showSkillDialog = false          // Default value
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

