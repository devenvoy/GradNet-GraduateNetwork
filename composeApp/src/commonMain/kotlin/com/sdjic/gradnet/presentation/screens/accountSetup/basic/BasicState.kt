package com.sdjic.gradnet.presentation.screens.accountSetup.basic

import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole

data class BasicState(
    val verificationField : String= "" ,
    val verificationFieldEnable: Boolean =true,
    val userRole: UserRole = UserRole.Alumni
)