package com.sdjic.gradnet.presentation.screens.auth.login

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import com.sdjic.gradnet.presentation.helper.LoginUiState
import com.sdjic.gradnet.presentation.helper.UiState
import kotlinx.coroutines.flow.MutableStateFlow

class LoginScreenModel : ScreenModel {

    val email = mutableStateOf(TextFieldValue(""))
    val password = mutableStateOf(TextFieldValue(""))
    private val _loginState = MutableStateFlow<LoginUiState>(UiState.Idle)
    val loginState: MutableStateFlow<LoginUiState> = _loginState

    fun login() {

    }
}