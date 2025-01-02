package com.sdjic.gradnet.presentation.screens.accountSetup.profession

import com.sdjic.gradnet.presentation.core.model.ExperienceModel

sealed interface ProfessionScreenAction {
    class OnAddExperience(val experience: ExperienceModel) : ProfessionScreenAction
    class OnUpdateExperience(val index: Int, val experience: ExperienceModel) : ProfessionScreenAction
    class OnRemoveExperience(val index: Int) : ProfessionScreenAction
    class OnUpdateLinkedinUrl(val url: String) : ProfessionScreenAction
    class OnUpdateGithubUrl(val url: String) : ProfessionScreenAction
    class OnUpdateTwitterUrl(val url: String) : ProfessionScreenAction
    class OnAddOtherUrl(val url: String) : ProfessionScreenAction
    class OnRemoveOtherUrl(val url: String) : ProfessionScreenAction
}