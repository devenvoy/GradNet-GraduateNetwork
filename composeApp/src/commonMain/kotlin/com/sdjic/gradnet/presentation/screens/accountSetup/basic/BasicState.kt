package com.sdjic.gradnet.presentation.screens.accountSetup.basic

import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole

data class BasicState(
    val verificationField: String = "",
    val otpField: String = "",
    val otpEmailField: String = "",
    val isVerified: Boolean = false,
    val showOtpBottomSheet: Boolean = false,
    val userRole: UserRole = UserRole.Alumni
)