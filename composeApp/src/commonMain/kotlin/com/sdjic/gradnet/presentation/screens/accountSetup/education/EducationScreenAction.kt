package com.sdjic.gradnet.presentation.screens.accountSetup.education

import com.sdjic.gradnet.presentation.core.model.EducationModel

sealed interface EducationScreenAction {
    class OnAddSkill(val skill: String) : EducationScreenAction
    class OnRemoveSkill(val skill: String) : EducationScreenAction
    class OnAddLanguage(val language: String) : EducationScreenAction
    class OnRemoveLanguage(val language: String) : EducationScreenAction
    class OnAddEducation(val education: EducationModel) : EducationScreenAction
    class OnUpdateEducation(val index: Int, val education: EducationModel) : EducationScreenAction
    class OnRemoveEducation(val index: Int) : EducationScreenAction
    class OnEducationBottomSheetStateChange(val value: Boolean): EducationScreenAction
}
