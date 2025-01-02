package com.sdjic.gradnet.presentation.screens.accountSetup.education

import com.sdjic.gradnet.presentation.core.model.EducationModel

data class EducationState(
    val skills: List<String> = emptyList(),
    val languages: List<String> = emptyList(),
    val eductionList: List<EducationModel> = emptyList(),
    val showEducationBottomSheet: Boolean = false
)
