package com.sdjic.gradnet.presentation.screens.accountSetup.profession

import com.sdjic.gradnet.presentation.core.model.ExperienceModel

data class ProfessionState(
    val linkedinUrl: String = "",
    val githubUrl: String = "",
    val twitterUrl: String = "",
    val showExperienceBottomSheet: Boolean = false,
    val showAddOtherUrlDialog: Boolean = false,
    val otherUrls: List<String> = emptyList(),
    val experienceList: List<ExperienceModel> = emptyList()
)
