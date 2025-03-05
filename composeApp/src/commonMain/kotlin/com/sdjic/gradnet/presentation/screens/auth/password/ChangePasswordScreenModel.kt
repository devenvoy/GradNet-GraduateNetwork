package com.sdjic.gradnet.presentation.screens.auth.password

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.repo.AuthRepository
import com.sdjic.gradnet.presentation.helper.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChangePasswordScreenModel(
    private val authRepository: AuthRepository,
    private val pref: AppCacheSetting
) : ScreenModel {

    val isLoggedIn = pref.isLoggedIn
    val oldPassword = mutableStateOf("")
    val newPassword = mutableStateOf("")
    val confirmPassword = mutableStateOf("")

    private val _uiState = MutableStateFlow<ChangePasswordState>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun onOldPasswordChange(value: String) {
        oldPassword.value = value
    }

    fun onNewPasswordChange(value: String) {
        newPassword.value = value
    }

    fun onConfirmPasswordChange(value: String) {
        confirmPassword.value = value
    }

    fun changePassword() {
        screenModelScope.launch {
            _uiState.update { UiState.Loading }
            if (oldPassword.value.isEmpty() || newPassword.value.isEmpty() || confirmPassword.value.isEmpty()) {
                _uiState.update { UiState.Error("Please fill all fields") }
                return@launch
            }
            if (newPassword.value != confirmPassword.value) {
                _uiState.update { UiState.Error("Passwords do not match") }
                return@launch
            }
            authRepository.updatePassword(
                accessToken = pref.accessToken,
                oldPassword = oldPassword.value,
                newPassword = newPassword.value
            ).onSuccess { r ->
                _uiState.update { UiState.Success(r.detail) }
            }.onError { e ->
                _uiState.update { UiState.Error(e.detail) }
            }
        }
    }
}