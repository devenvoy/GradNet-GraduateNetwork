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

            BasicScreenAction.ResendOtp -> verifyOtp()
            BasicScreenAction.VerifyOtp -> resendOtp()
        }
    }

    private fun verifyOtp() {}

    private fun resendOtp() {}

}
