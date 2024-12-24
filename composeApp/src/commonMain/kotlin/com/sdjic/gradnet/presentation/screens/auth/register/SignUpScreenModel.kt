package com.sdjic.gradnet.presentation.screens.auth.register

import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.presentation.helper.SignUpUiState
import com.sdjic.gradnet.presentation.helper.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpScreenModel : ScreenModel {

    private val _name = MutableStateFlow(TextFieldValue(""))
    val name = _name.asStateFlow()

    private val _email = MutableStateFlow(TextFieldValue(""))
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow(TextFieldValue(""))
    val password = _password.asStateFlow()

    private val _signUpState = MutableStateFlow<SignUpUiState>(UiState.Idle)
    val signUpState = _signUpState.asStateFlow()


    fun onNameChange(newValue: TextFieldValue) {
        _name.value = newValue
    }

    fun onEmailChange(newValue: TextFieldValue) {
        _email.value = newValue
    }

    fun onPasswordChange(newValue: TextFieldValue) {
        _password.value = newValue
    }

    fun signUp() {
        screenModelScope.launch {
            val validationResult = validateInputs()
            if (validationResult != null) {
                _signUpState.value = UiState.ValidationError(validationResult)
                _signUpState.value = UiState.Idle
                return@launch
            }
            _signUpState.value = UiState.Loading
        }
    }

    private fun validateInputs(): Map<String, List<String>>? {
        val errors = mutableMapOf<String, MutableList<String>>()

        if (_name.value.text.isBlank()) {
            errors.getOrPut("name") { mutableListOf() }.add("Name cannot be empty.")
        }

        if (_email.value.text.isBlank()) {
            errors.getOrPut("email") { mutableListOf() }.add("Email cannot be empty.")
        } else if (!isValidEmail(_email.value.text)) {
            errors.getOrPut("email") { mutableListOf() }.add("Invalid email format.")
        }

        if (_password.value.text.isBlank()) {
            errors.getOrPut("password") { mutableListOf() }.add("Password cannot be empty.")
        } else if (_password.value.text.length < 6) {
            errors.getOrPut("password") { mutableListOf() }
                .add("Password must be at least 6 characters long.")
        }

        return if (errors.isEmpty()) null else errors
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email.matches(Regex(emailRegex))
    }

    fun verifyOtp() {    }

    fun resendOtp() {    }
}