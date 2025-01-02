package com.sdjic.gradnet.presentation.screens.accountSetup.education

import com.sdjic.gradnet.presentation.core.model.EducationModel

data class EducationState(
    val skills: List<String> = emptyList(),
    val languages: List<String> = emptyList(),
    val eductionList: List<EducationModel> = educations,
    val showEducationBottomSheet: Boolean = false,
    val showLanguageDialog : Boolean = false,
    val showSkillDialog: Boolean = false
)

val educations = listOf(
    EducationModel(
        id = 1, schoolName = "Harvard University", degree = "Bachelor's", field = "Computer Science",
        location = "Cambridge, MA", description = "Focused on AI and Machine Learning.",
        startDate = "Aug 2015", endDate = "May 2019"
    ),
    EducationModel(
        id = 2, schoolName = "Stanford University", degree = "Master's", field = "Data Science",
        location = "Stanford, CA", description = "Specialized in Big Data Analytics.",
        startDate = "Sep 2019", endDate = "Jun 2021"
    )
)

