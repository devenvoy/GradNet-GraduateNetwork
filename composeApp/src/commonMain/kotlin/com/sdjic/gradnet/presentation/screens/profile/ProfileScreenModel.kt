package com.sdjic.gradnet.presentation.screens.profile

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
import com.sdjic.gradnet.presentation.helper.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileScreenModel(
    private val userDataSource: UserDataSource,
    private val userRepository: UserRepository,
    private val prefs: AppCacheSetting
) : ScreenModel {

    private val _profileState = MutableStateFlow<ProfileScreenState>(UiState.Loading)
    val profileState = _profileState.asStateFlow()

    private val _isReadOnlyMode = MutableStateFlow(false)
    val isReadOnlyMode = _isReadOnlyMode.asStateFlow()

    fun updateEditMode(value: Boolean) {
        _isReadOnlyMode.update { value }
    }

    fun loadUserProfile() {
        screenModelScope.launch {
            try {
                var result = prefs.getUserProfile()
                _profileState.update { UiState.Success(result) }
                if (!prefs.firstInitialized) {
                    userRepository.fetchProfile(prefs.accessToken).onSuccess {
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

                _profileState.update { UiState.Success(result) }
            } catch (e: Exception) {
            }
        }
    }


    private suspend fun updateUserPreference(user: UserProfileResponse) {
        try {
            val currentUser = user.toUserProfile()
            _profileState.update { UiState.Success(currentUser) }
            prefs.isVerified = user.verified
            prefs.saveUserProfile(currentUser)
            userDataSource.upsertAllUrls(user.urls.map { it.toUrlTable() })
            userDataSource.upsertAllEducations(user.education.map { it.toEducationTable() })
            userDataSource.upsertAllExperiences(user.experience.map { it.toExperienceTable() })
        } catch (e: Exception) {
        }
    }

    fun fetchAndLoadUserProfile(userId: String) {
        updateEditMode(true)
        screenModelScope.launch {
            _profileState.update { UiState.Loading }
            try {
                userRepository.fetchUser(userId).onSuccess { r->
                    r.value?.let {
                    _profileState.update { UiState.Success(r.value.toUserProfile()) }
                    } ?: run {
                        _profileState.update { UiState.Error("User not found") }
                    }
                }.onError { e->
                    _profileState.update { UiState.Error(e.detail) }
                }
            } catch (e: Exception) {
                _profileState.update { UiState.Error("${e.message}") }
            }
        }
    }
}