package com.sdjic.gradnet.presentation.screens.accountSetup

import cafe.adriel.voyager.core.model.ScreenModel
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicScreenAction
import com.sdjic.gradnet.presentation.screens.accountSetup.basic.BasicState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SetUpAccountViewModel : ScreenModel{

    private val _basicState = MutableStateFlow(BasicState())
    val basicState = _basicState.asStateFlow()

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
        }
    }

    private fun verifyOtp() {}

    private fun resendOtp() {}

}
