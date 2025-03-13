package com.sdjic.gradnet.presentation.screens.auth.password

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.repo.AuthRepository
import kotlinx.coroutines.launch

class ForgotPasswordScreenModel(
    private val authRepository: AuthRepository,
    private val prefs: AppCacheSetting
) : ScreenModel {


    val email = mutableStateOf(TextFieldValue(""))

    fun sendLink() {
        screenModelScope.launch {

        }
    }
}