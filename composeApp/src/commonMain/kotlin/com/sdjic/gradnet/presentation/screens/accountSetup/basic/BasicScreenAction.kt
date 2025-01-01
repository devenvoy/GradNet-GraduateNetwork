package com.sdjic.gradnet.presentation.screens.accountSetup.basic

sealed interface BasicScreenAction {
    class OnVerificationFieldValueChange(val value: String) : BasicScreenAction
    class OnOtpFieldValueChange(val value: String) : BasicScreenAction
    class OnOtpBottomSheetStateChange(val value: Boolean): BasicScreenAction
    data object VerifyOtp : BasicScreenAction
    data object ResendOtp : BasicScreenAction
}
