package com.sdjic.gradnet.presentation.screens.accountSetup.profession

import com.sdjic.gradnet.presentation.core.model.ExperienceModel

data class ProfessionState(
    val linkedinUrl: String = "",
    val githubUrl: String = "",
    val twitterUrl: String = "",
    val showExperienceBottomSheet: Boolean = false,
    val showAddOtherUrlDialog: Boolean = false,
    val otherUrls: List<String> = emptyList(),
    val experienceList: List<ExperienceModel> = experiences
)

val experiences = listOf(
    ExperienceModel(
        id = 1, title = "Software Engineer", company = "ABC Corp", location = "New York",
        description = "Developed and maintained mobile applications.",
        startDate = "Jan 2021", endDate = "Dec 2022"
    ),
    ExperienceModel(
        id = 2, title = "Junior Developer", company = "XYZ Ltd", location = "San Francisco",
        description = "Assisted in the development of web apps.",
        startDate = "Jun 2019", endDate = "Jan 2021"
    )
)