package com.sdjic.gradnet.presentation.screens.accountSetup.basic

import androidx.compose.ui.graphics.ImageBitmap

data class BasicState(
    val verificationField: String = "",
    val nameField: String = "",
    val aboutField: String = "",
    val addressField: String = "",
    val contactField: String = "",
    val emailField: String = "",
    val showContactsToOthers: Boolean = true,
    val profileImage: ImageBitmap? = null,
    val backgroundImage: ImageBitmap? = null,
    val profileImageUrl: String? = null,
    val backGroundImageUrl: String? = null,
    val openBackGroundImagePicker: Boolean = false,
    val openProfileImagePicker: Boolean = false,
)