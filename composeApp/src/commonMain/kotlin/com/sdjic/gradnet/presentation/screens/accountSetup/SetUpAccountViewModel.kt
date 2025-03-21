package com.sdjic.gradnet.presentation.screens.accountSetup

import androidx.compose.ui.graphics.ImageBitmap
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.entity.response.UserProfileResponse
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.data.network.utils.toEducationModel
import com.sdjic.gradnet.data.network.utils.toEducationTable
import com.sdjic.gradnet.data.network.utils.toExperienceModel
import com.sdjic.gradnet.data.network.utils.toExperienceTable
import com.sdjic.gradnet.data.network.utils.toSocialUrls
import com.sdjic.gradnet.data.network.utils.toUrlDto
import com.sdjic.gradnet.data.network.utils.toUrlTable
import com.sdjic.gradnet.data.network.utils.toUserProfile
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.repo.UserDataSource
import com.sdjic.gradnet.domain.repo.UserRepository
import com.sdjic.gradnet.presentation.core.model.UserProfile
import com.sdjic.gradnet.presentation.core.model.toBasicState
import com.sdjic.gradnet.presentation.core.model.toEducationState
import com.sdjic.gradnet.presentation.core.model.toProfessionState
import com.sdjic.gradnet.presentation.helper.FetchUserUiState
import com.sdjic.gradnet.presentation.helper.SetUpOrEditUiState
import com.sdjic.gradnet.presentation.helper.UiState
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicScreenAction
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicState
import com.sdjic.gradnet.presentation.screens.accountSetup.education.EducationScreenAction
import com.sdjic.gradnet.presentation.screens.accountSetup.education.EducationState
import com.sdjic.gradnet.presentation.screens.accountSetup.profession.ProfessionScreenAction
import com.sdjic.gradnet.presentation.screens.accountSetup.profession.ProfessionState
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SetUpAccountViewModel(
    private val prefs:AppCacheSetting,
    private val userRepository: UserRepository,
    private val userDataSource: UserDataSource
) : ScreenModel {

    private val _basicState = MutableStateFlow(BasicState())
    val basicState = _basicState.asStateFlow()

    private val _educationState = MutableStateFlow(EducationState())
    val educationState = _educationState.asStateFlow()

    private val _professionState = MutableStateFlow(ProfessionState())
    val professionState: StateFlow<ProfessionState> get() = _professionState.asStateFlow()

    private val _isVerified = MutableStateFlow(prefs.isVerified)
    val isVerified = _isVerified.asStateFlow()

    private val _userRole = MutableStateFlow(UserRole.getUserRole(prefs.userRole))
    val userRole = _userRole.asStateFlow()

    private val _userData = MutableStateFlow<FetchUserUiState>(UiState.Idle)
    val userData = _userData.asStateFlow()

    private val _setUpOrEditState = MutableStateFlow<SetUpOrEditUiState>(UiState.Idle)
    val setUpOrEditState = _setUpOrEditState.asStateFlow()


    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        screenModelScope.launch {
            _userData.value = UiState.Loading
            try {
                var result = prefs.getUserProfile()
                if (!_isVerified.value or !prefs.firstInitialized) {
                    userRepository.fetchProfile(prefs.accessToken.orEmpty()).onSuccess {
                        it.value?.let { up ->
                            prefs.firstInitialized = true
                            updateUserPreference(up)
                            result = up.toUserProfile()
                        }
                    }
                }
                val educationList = userDataSource.getAllEducations().map { it.toEducationModel() }
                val experienceList =
                    userDataSource.getAllExperiences().map { it.toExperienceModel() }
                val urlList = userDataSource.getAllUrls().map { it.toUrlDto() }

                result = result.copy(
                    educations = educationList,
                    experiences = experienceList,
                    socialUrls = urlList.toSocialUrls()
                )

                updateUserDataState(user = result)
            } catch (e: Exception) {
                _userData.value = UiState.Error(e.message)
            }
        }
    }

    private fun updateUserDataState(user: UserProfile) {
        _basicState.update { user.toBasicState() }
        _educationState.update { user.toEducationState() }
        _professionState.update { user.toProfessionState() }
        _userData.value = UiState.Success(user)
    }

    private suspend fun updateUserPreference(user: UserProfileResponse) {
        try {
            val userProfile = user.toUserProfile()
            updateUserDataState(userProfile)
            prefs.isVerified = user.verified
            prefs.saveUserProfile(userProfile)
            userDataSource.upsertAllUrls(user.urls.map { it.toUrlTable() })
            userDataSource.upsertAllEducations(user.education.map { it.toEducationTable() })
            userDataSource.upsertAllExperiences(user.experience.map { it.toExperienceTable() })
        } catch (e: Exception) {
            _userData.value = UiState.Error(e.message)
        }
    }

    fun onBasicAction(basicScreenAction: BasicScreenAction) {
        when (basicScreenAction) {

            is BasicScreenAction.OnVerificationFieldValueChange -> {
                _basicState.value =
                    _basicState.value.copy(verificationField = basicScreenAction.value)
            }

            is BasicScreenAction.OnBackGroundDialogState -> {
                _basicState.value =
                    _basicState.value.copy(openBackGroundImagePicker = basicScreenAction.value)
            }

            is BasicScreenAction.OnBackgroundImageChange -> {
                uploadImage(basicScreenAction.value, "BACKGROUND")
                _basicState.value =
                    _basicState.value.copy(backgroundImage = basicScreenAction.value)
            }

            is BasicScreenAction.OnProfileDialogState -> {
                _basicState.value =
                    _basicState.value.copy(openProfileImagePicker = basicScreenAction.value)
            }

            is BasicScreenAction.OnProfileImageChange -> {
                uploadImage(basicScreenAction.value, "PROFILE")
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

            is BasicScreenAction.OnShowContactsToOthersChange -> {
                _basicState.value =
                    _basicState.value.copy(showContactsToOthers = basicScreenAction.value)
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
                    if (_educationState.value.eductionList.isEmpty()) return@launch
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
                    val updatedExperiences =
                        _professionState.value.experienceList + action.experience
                    _professionState.value.copy(experienceList = updatedExperiences)
                }

                is ProfessionScreenAction.OnUpdateExperience -> {
                    val updatedExperiences =
                        _professionState.value.experienceList.toMutableList().apply {
                            this[action.index] = action.experience
                        }
                    _professionState.value.copy(experienceList = updatedExperiences)
                }

                is ProfessionScreenAction.OnRemoveExperience -> {
                    if (_professionState.value.experienceList.isEmpty()) return@launch
                    val updatedExperiences =
                        _professionState.value.experienceList.toMutableList().apply {
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

    fun showErrorState(message: String) {
        screenModelScope.launch {
            if (_setUpOrEditState.value != UiState.Loading) {
                _setUpOrEditState.value = UiState.Loading
                delay(1000L)
            }
            _setUpOrEditState.value = UiState.Error(message)
        }
    }

    private fun uploadImage(image: ImageBitmap, type: String) = screenModelScope.launch {
        _setUpOrEditState.update { UiState.Loading }
        userRepository.updateUserImages(
            token = prefs.accessToken.orEmpty(),
            imageBitmap = image,
            type = type
        ).apply {
            onSuccess { r ->
                r.value?.let { user ->
                    updateUserPreference(user)
                    _setUpOrEditState.update { UiState.Success(r.detail) }
                }
            }
            onError { e ->
                _setUpOrEditState.update { UiState.Error(e.detail) }
            }
        }
    }

    fun updateUserProfile() {
        screenModelScope.launch {
            _setUpOrEditState.update { UiState.Loading }
            userRepository.updateUser(
                prefs.userRole,
                prefs.accessToken.orEmpty(),
                _basicState.value,
                _educationState.value,
                _professionState.value
            )
                .onSuccess { r ->
                    if (r.value != null) {
                        updateUserPreference(r.value)
                        _setUpOrEditState.update { UiState.Success(r.detail) }
                    } else {
                        _setUpOrEditState.update { UiState.Error(r.detail) }
                    }
                }
                .onError { e ->
                    _setUpOrEditState.update { UiState.Error(e.detail) }
                }
        }
    }
}

