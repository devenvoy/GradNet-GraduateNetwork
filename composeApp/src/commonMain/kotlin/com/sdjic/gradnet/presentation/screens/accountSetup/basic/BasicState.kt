package com.sdjic.gradnet.presentation.screens.accountSetup.basic

import androidx.compose.ui.graphics.ImageBitmap

data class BasicState(
    val verificationField: String = "",
    val otpField: String = "",
    val otpEmailField: String = "",
    val nameField: String = "",
    val aboutField: String = "",
    val addressField: String = "",
    val backgroundImage: ImageBitmap? = null,
    val profileImage: ImageBitmap? = null,
    val showOtpBottomSheet: Boolean = false,
    val openBackGroundImagePicker : Boolean = false,
    val openProfileImagePicker : Boolean = false,
)