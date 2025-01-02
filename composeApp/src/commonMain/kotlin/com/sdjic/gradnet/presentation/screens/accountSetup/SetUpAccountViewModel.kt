package com.sdjic.gradnet.presentation.screens.accountSetup

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.repo.UserRepository
import com.sdjic.gradnet.presentation.helper.SetUpOrEditUiState
import com.sdjic.gradnet.presentation.helper.UiState
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicScreenAction
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicState
import com.sdjic.gradnet.presentation.screens.accountSetup.education.EducationScreenAction
import com.sdjic.gradnet.presentation.screens.accountSetup.education.EducationState
import com.sdjic.gradnet.presentation.screens.accountSetup.profession.ProfessionScreenAction
import com.sdjic.gradnet.presentation.screens.accountSetup.profession.ProfessionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin

class SetUpAccountViewModel(
    private val userRepository: UserRepository
) : ScreenModel {

    private val _basicState = MutableStateFlow(BasicState())
    val basicState = _basicState.asStateFlow()

    private val _educationState = MutableStateFlow(EducationState())
    val educationState = _educationState.asStateFlow()

    private val _isVerified = MutableStateFlow(false)
    val isVerified = _isVerified.asStateFlow()

    private val _professionState = MutableStateFlow(ProfessionState())
    val professionState: StateFlow<ProfessionState> get() = _professionState.asStateFlow()

    private val _userData = MutableStateFlow<SetUpOrEditUiState>(UiState.Idle)
    val userData = _userData.asStateFlow()

    private val prefs = getKoin().get<AppCacheSetting>()

    init {
        fetchUserDetails()
    }

    fun fetchUserDetails() {
        screenModelScope.launch {
            _userData.value = UiState.Loading
            val result = userRepository.fetchUser(prefs.accessToken)
            result.onSuccess {
                _userData.value = UiState.Success(it)
            }.onError {
                _userData.value = UiState.Error(it.detail)
            }
        }
    }


    fun onBasicAction(basicScreenAction: BasicScreenAction) {
        when (basicScreenAction) {
            BasicScreenAction.ResendOtp -> verifyOtp()
            BasicScreenAction.VerifyOtp -> resendOtp()

            is BasicScreenAction.OnVerificationFieldValueChange -> {
                _basicState.value =
                    _basicState.value.copy(verificationField = basicScreenAction.value)
            }

            is BasicScreenAction.OnOtpFieldValueChange -> {
                _basicState.value =
                    _basicState.value.copy(otpField = basicScreenAction.value)
            }

            is BasicScreenAction.OnOtpBottomSheetStateChange -> {
                _basicState.value =
                    _basicState.value.copy(showOtpBottomSheet = basicScreenAction.value)
            }

            is BasicScreenAction.OnBackGroundDialogState -> {
                _basicState.value =
                    _basicState.value.copy(openBackGroundImagePicker = basicScreenAction.value)
            }

            is BasicScreenAction.OnBackgroundImageChange -> {
                _basicState.value =
                    _basicState.value.copy(backgroundImage = basicScreenAction.value)
            }

            is BasicScreenAction.OnProfileDialogState -> {
                _basicState.value =
                    _basicState.value.copy(openProfileImagePicker = basicScreenAction.value)
            }

            is BasicScreenAction.OnProfileImageChange -> {
                _basicState.value =
                    _basicState.value.copy(profileImage = basicScreenAction.value)
            }

            is BasicScreenAction.OnAboutFieldValueChange -> {
                _basicState.value =
                    _basicState.value.copy(aboutField = basicScreenAction.value)
            }

            is BasicScreenAction.OnAddressFieldValueChange -> {
                _basicState.value =
                    _basicState.value.copy(addressField = basicScreenAction.value)
            }

            is BasicScreenAction.OnNameFieldValueChange -> {
                _basicState.value =
                    _basicState.value.copy(nameField = basicScreenAction.value)
            }
        }
    }

    fun onEducationAction(educationScreenAction: EducationScreenAction) {
        screenModelScope.launch {
            when (educationScreenAction) {
                is EducationScreenAction.OnAddEducation -> {
                    _educationState.value = _educationState.value.copy(
                        eductionList = _educationState.value.eductionList + educationScreenAction.education
                    )
                }

                is EducationScreenAction.OnUpdateEducation -> {
                    val updatedEducationList =
                        _educationState.value.eductionList.toMutableList().apply {
                            this[educationScreenAction.index] = educationScreenAction.education
                        }
                    _educationState.value =
                        _educationState.value.copy(eductionList = updatedEducationList)
                }

                is EducationScreenAction.OnRemoveEducation -> {
                    val updatedEducationList =
                        _educationState.value.eductionList.toMutableList().apply {
                            removeAt(educationScreenAction.index)
                        }
                    _educationState.value =
                        _educationState.value.copy(eductionList = updatedEducationList)
                }

                is EducationScreenAction.OnAddLanguage -> {
                    if (educationScreenAction.language.isNotEmpty()) {
                        _educationState.value = _educationState.value.copy(
                            languages = _educationState.value.languages + educationScreenAction.language
                        )
                    }
                }

                is EducationScreenAction.OnRemoveLanguage -> {
                    _educationState.value = _educationState.value.copy(
                        languages = _educationState.value.languages.toMutableList().apply {
                            remove(educationScreenAction.language)
                        }
                    )
                }

                is EducationScreenAction.OnAddSkill -> {
                    if (educationScreenAction.skill.isNotEmpty()) {
                        _educationState.value = _educationState.value.copy(
                            skills = _educationState.value.skills + educationScreenAction.skill
                        )
                    }
                }

                is EducationScreenAction.OnRemoveSkill -> {
                    _educationState.value = _educationState.value.copy(
                        skills = _educationState.value.skills.toMutableList().apply {
                            remove(educationScreenAction.skill)
                        }
                    )
                }

                is EducationScreenAction.OnEducationBottomSheetStateChange -> {
                    _educationState.value = _educationState.value.copy(
                        showEducationBottomSheet = educationScreenAction.value
                    )
                }

                is EducationScreenAction.OnLanguageDialogStateChange -> {
                    _educationState.value = _educationState.value.copy(
                        showLanguageDialog = educationScreenAction.value
                    )
                }

                is EducationScreenAction.OnSkillDialogStateChange -> {
                    _educationState.value = _educationState.value.copy(
                        showSkillDialog = educationScreenAction.value
                    )
                }
            }
        }
    }

    fun onProfessionAction(action: ProfessionScreenAction) {
        screenModelScope.launch {
            _professionState.value = when (action) {
                is ProfessionScreenAction.OnAddExperience -> {
                    val updatedExperiences = _professionState.value.experienceList + action.experience
                    _professionState.value.copy(experienceList = updatedExperiences)
                }
                is ProfessionScreenAction.OnUpdateExperience -> {
                    val updatedExperiences = _professionState.value.experienceList.toMutableList().apply {
                        this[action.index] = action.experience
                    }
                    _professionState.value.copy(experienceList = updatedExperiences)
                }
                is ProfessionScreenAction.OnRemoveExperience -> {
                    val updatedExperiences = _professionState.value.experienceList.toMutableList().apply {
                        removeAt(action.index)
                    }
                    _professionState.value.copy(experienceList = updatedExperiences)
                }
                is ProfessionScreenAction.OnUpdateLinkedinUrl -> {
                    _professionState.value.copy(linkedinUrl = action.url)
                }
                is ProfessionScreenAction.OnUpdateGithubUrl -> {
                    _professionState.value.copy(githubUrl = action.url)
                }
                is ProfessionScreenAction.OnUpdateTwitterUrl -> {
                    _professionState.value.copy(twitterUrl = action.url)
                }

                is ProfessionScreenAction.OnAddOtherUrl -> {
                    if (action.value.isNotEmpty()) {
                        _professionState.value.copy(otherUrls = _professionState.value.otherUrls + action.value)
                    } else {
                        _professionState.value
                    }
                }
                is ProfessionScreenAction.OnRemoveOtherUrl -> {
                    _professionState.value.copy(
                        otherUrls = _professionState.value.otherUrls.toMutableList().apply {
                            remove(action.value)
                        }
                    )
                }

                is ProfessionScreenAction.OnExperienceBottomSheetStateChange -> {
                    _professionState.value.copy(showExperienceBottomSheet = action.value)
                }

                is ProfessionScreenAction.OnAddOtherUrlDialogStateChange -> {
                    _professionState.value.copy(showAddOtherUrlDialog = action.value)
                }
            }
        }
    }


    private fun verifyOtp() {}

    private fun resendOtp() {}

}
