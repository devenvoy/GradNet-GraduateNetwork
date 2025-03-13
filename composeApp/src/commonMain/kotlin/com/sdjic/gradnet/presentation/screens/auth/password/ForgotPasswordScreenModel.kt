package com.sdjic.gradnet.presentation.screens.auth.password

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.domain.repo.AuthRepository
import com.sdjic.gradnet.presentation.helper.ConnectivityManager
import com.sdjic.gradnet.presentation.helper.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgotPasswordScreenModel(
    private val authRepository: AuthRepository
) : ScreenModel {


    val email = mutableStateOf(TextFieldValue(""))


    private val _uiState = MutableStateFlow<ForgotPasswordState>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun sendLink() {
        screenModelScope.launch {
            _uiState.value = UiState.Loading
            val validationResult = validateInputs()
            if (validationResult != null) {
                _uiState.value = UiState.ValidationError(validationResult)
                return@launch
            }
            if (ConnectivityManager.isConnected) {
                val result = authRepository.forgotPassword(email.value.text)
                result.onSuccess {
                    _uiState.value = UiState.Success(it)
                }.onError {
                    _uiState.value = UiState.Error(it.detail)
                }
            } else {
                _uiState.value = UiState.Error("Not Connected to Internet")
            }
        }
    }

    private fun validateInputs(): List<String>? {
        val errors = mutableListOf<String>()

        if (email.value.text.isEmpty()) {
            errors.add("Please Enter an email address")
        } else if (!isValidEmail(email.value.text)) {
            errors.add("Invalid email format.")
        }

        return if (errors.isEmpty()) null else errors
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email.matches(Regex(emailRegex))
    }
}