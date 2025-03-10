package com.sdjic.gradnet.presentation.screens.verification

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.data.network.utils.toUserProfile
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.repo.UserRepository
import com.sdjic.gradnet.presentation.helper.ConnectivityManager
import com.sdjic.gradnet.presentation.helper.UiState
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserVerificationScreenModel(
    private val userRepository: UserRepository,
    private val prefs: AppCacheSetting
) : ScreenModel {

    private val _verificationState = MutableStateFlow<UserVerificationState>(UiState.Idle)
    val verificationState = _verificationState.asStateFlow()

    private val isConnected = ConnectivityManager.isConnected
    val userRole = UserRole.getUserRole(prefs.userRole)

    private val _showOtpBottomSheet = MutableStateFlow(false)
    val showOtpBottomSheet = _showOtpBottomSheet.asStateFlow()

    private val _otpField = MutableStateFlow("")
    val otpField = _otpField.asStateFlow()

    private val _otpEmailField = MutableStateFlow("")
    val otpEmailField = _otpEmailField.asStateFlow()

    private val _verificationField = MutableStateFlow("")
    val verificationField = _verificationField.asStateFlow()

    private val _isUserVerified = MutableStateFlow(prefs.isVerified)
    val isUserVerified = _isUserVerified.asStateFlow()


    fun onOtpFieldValueChange(value: String) {
        _otpField.update { value }
    }

    fun onOtpBottomSheetStateChange(value: Boolean) {
        _showOtpBottomSheet.update { value }
    }

    fun onVerificationFieldValueChange(value: String) {
        _verificationField.update { value }
    }


    fun verifyOtp() {
        screenModelScope.launch {
            _verificationState.update { UiState.Loading }
            if (!isConnected) {
                delay(500)
                _verificationState.update { UiState.Error("No internet connection") }
                return@launch
            }
            val result = userRepository.verifyOtp(
                _verificationField.value,
                _otpField.value,
                token = prefs.accessToken
            )
            result.onSuccess { r ->
                r.value?.let { user ->
                    val userProfile = user.toUserProfile()
                    prefs.isVerified = user.verified
                    prefs.saveUserProfile(userProfile)
                    _isUserVerified.value = prefs.isVerified
                    _showOtpBottomSheet.update { false }
                    _verificationState.update { UiState.Success(r.status) }
                }
            }.onError { e ->
                _verificationState.update { UiState.Error(e.detail) }
                _showOtpBottomSheet.update { false }
            }
        }
    }


    fun resendOtp() {
        screenModelScope.launch {
            _verificationState.update { UiState.Loading }
            if (!isConnected) {
                delay(500)
                _verificationState.update { UiState.Error("No internet connection") }
                return@launch
            }
            onOtpFieldValueChange("")
            userRepository.sendOtp(_verificationField.value)
                .onSuccess { r ->
                    _verificationState.update { UiState.Success(r.status) }
                    _otpEmailField.update { r.value?.email ?: "" }
                    _showOtpBottomSheet.update { true }
                }
                .onError { e ->
                    _verificationState.update { UiState.Error(e.detail) }
                    _showOtpBottomSheet.update { false }
                }
        }
    }
}