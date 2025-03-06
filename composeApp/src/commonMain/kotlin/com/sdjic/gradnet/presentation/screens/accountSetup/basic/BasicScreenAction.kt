package com.sdjic.gradnet.presentation.screens.accountSetup.basic

import androidx.compose.ui.graphics.ImageBitmap

sealed interface BasicScreenAction {
    class OnVerificationFieldValueChange(val value: String) : BasicScreenAction
    class OnNameFieldValueChange(val value: String) : BasicScreenAction
    class OnAddressFieldValueChange(val value: String) : BasicScreenAction
    class OnAboutFieldValueChange(val value: String) : BasicScreenAction
    class OnBackGroundDialogState(val value: Boolean): BasicScreenAction
    class OnProfileDialogState(val value: Boolean): BasicScreenAction
    class OnProfileImageChange(val value: ImageBitmap): BasicScreenAction
    class OnBackgroundImageChange(val value: ImageBitmap): BasicScreenAction
}

