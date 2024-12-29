package com.sdjic.gradnet.presentation.screens.auth.register

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.entity.SignUpRequest
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.domain.repo.AuthRepository
import com.sdjic.gradnet.presentation.helper.ConnectivityManager
import com.sdjic.gradnet.presentation.helper.SignUpUiState
import com.sdjic.gradnet.presentation.helper.UiState
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole
import com.sdjic.gradnet.presentation.screens.auth.register.model.getUserRoles
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpScreenModel(private val authRepository: AuthRepository) : ScreenModel {

    private val _name = MutableStateFlow(TextFieldValue(""))
    val name = _name.asStateFlow()

    private val _email = MutableStateFlow(TextFieldValue(""))
    val email = _email.asStateFlow()

    private val _phone = MutableStateFlow(TextFieldValue(""))
    val phone = _phone.asStateFlow()

    private val _password = MutableStateFlow(TextFieldValue(""))
    val password = _password.asStateFlow()

    private val _signUpState = MutableStateFlow<SignUpUiState>(UiState.Idle)
    val signUpState = _signUpState.asStateFlow()

    val userRoles = mutableStateOf(getUserRoles())

    private val _selectedUserRole = MutableStateFlow(userRoles.value[0])
    val selectedUserRole = _selectedUserRole.asStateFlow()

    fun onUserRoleSelected(userRole: UserRole) {
        _selectedUserRole.value = userRole
    }

    fun onNameChange(newValue: TextFieldValue) {
        _name.value = newValue
    }

    fun onEmailChange(newValue: TextFieldValue) {
        _email.value = newValue
    }

    fun onPasswordChange(newValue: TextFieldValue) {
        _password.value = newValue
    }

    fun onPhoneChange(newValue: TextFieldValue) {
        _phone.value = newValue
    }

    fun signUp() {
        screenModelScope.launch {
            _signUpState.value = UiState.Loading
            val validationResult = validateInputs()
            if (validationResult != null) {
                _signUpState.value = UiState.ValidationError(validationResult)
                delay(200)
                _signUpState.value = UiState.Idle
                return@launch
            }
            val result = authRepository.signUp(
                SignUpRequest(
                    name = _name.value.text,
                    email = _email.value.text,
                    phoneNo = _phone.value.text,
                    password = _password.value.text,
                    address = "  "
                )
            )
            if(ConnectivityManager.isConnected){
                result.onSuccess {
                    _signUpState.value = UiState.Success(it)
                }.onError {
                    _signUpState.value = UiState.Error(it.detail)
                }
            }else{
                _signUpState.value = UiState.Error("Not Connected to Internet")
            }
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

        if (_selectedUserRole.value.name.isBlank()) {
            errors.getOrPut("role") { mutableListOf() }.add("Role cannot be empty.")
        }

        if (_phone.value.text.isBlank()) {
            errors.getOrPut("phone") { mutableListOf() }.add("Phone cannot be empty.")
        } else if (!isValidPhone(_phone.value.text)) {
            errors.getOrPut("phone") { mutableListOf() }.add("Invalid phone number.")
        }

        return if (errors.isEmpty()) null else errors
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email.matches(Regex(emailRegex))
    }

    private fun isValidPhone(phone: String): Boolean {
        val indianPhonePattern = "^[6-9]\\d{9}$"
        return phone.matches(indianPhonePattern.toRegex())
    }

    fun verifyOtp() {}

    fun resendOtp() {}

}