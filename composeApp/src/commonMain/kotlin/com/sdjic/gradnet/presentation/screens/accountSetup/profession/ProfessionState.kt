package com.sdjic.gradnet.presentation.screens.accountSetup.profession

import com.sdjic.gradnet.presentation.core.model.ExperienceModel

data class ProfessionState(
    val experiences: List<ExperienceModel> = emptyList(),
    val linkedinUrl: String = "",
    val githubUrl: String = "",
    val twitterUrl: String = "",
    val otherUrls: List<String> = emptyList()
)