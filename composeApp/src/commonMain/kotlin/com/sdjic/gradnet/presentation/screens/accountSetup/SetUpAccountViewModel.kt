package com.sdjic.gradnet.presentation.screens.accountSetup

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.repo.UserRepository
import com.sdjic.gradnet.presentation.helper.SetUpOrEditUiState
import com.sdjic.gradnet.presentation.helper.UiState
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BaseBasicScreenAction
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicScreenAction
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin

class SetUpAccountViewModel(
    private val userRepository: UserRepository
) : ScreenModel {

    private val _basicState = MutableStateFlow(BasicState())
    val basicState = _basicState.asStateFlow()

    private val _userData = MutableStateFlow<SetUpOrEditUiState>(UiState.Idle)
    val userData = _userData.asStateFlow()

    private val prefs = getKoin().get<AppCacheSetting>()

    init {
        fetchBaseUser()
    }

    fun fetchBaseUser() {
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
            BaseBasicScreenAction.ResendOtp -> verifyOtp()
            BaseBasicScreenAction.VerifyOtp -> resendOtp()

            is BaseBasicScreenAction.OnVerificationFieldValueChange -> {
                _basicState.value =
                    _basicState.value.copy(verificationField = basicScreenAction.value)
            }

            is BaseBasicScreenAction.OnOtpFieldValueChange -> {
                _basicState.value =
                    _basicState.value.copy(otpField = basicScreenAction.value)
            }

            is BaseBasicScreenAction.OnOtpBottomSheetStateChange -> {
                _basicState.value =
                    _basicState.value.copy(showOtpBottomSheet = basicScreenAction.value)
            }

            is BaseBasicScreenAction.OnBackGroundDialogState -> {
                _basicState.value =
                    _basicState.value.copy(openBackGroundImagePicker = basicScreenAction.value)
            }

            is BaseBasicScreenAction.OnBackgroundImageChange -> {
                _basicState.value =
                    _basicState.value.copy(backgroundImage = basicScreenAction.value)
            }

            is BaseBasicScreenAction.OnProfileDialogState -> {
                _basicState.value =
                    _basicState.value.copy(openProfileImagePicker = basicScreenAction.value)
            }

            is BaseBasicScreenAction.OnProfileImageChange -> {
                _basicState.value =
                    _basicState.value.copy(profileImage = basicScreenAction.value)
            }
        }
    }

    private fun verifyOtp() {}

    private fun resendOtp() {}

}
