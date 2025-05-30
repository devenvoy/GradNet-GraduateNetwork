package com.sdjic.gradnet.presentation.screens.auth.register

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.mmk.kmpauth.google.GoogleUser
import com.sdjic.gradnet.data.network.entity.response.SignUpRequest
import com.sdjic.gradnet.data.network.entity.response.SignUpResponse
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.domain.AppCacheSetting
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
import org.koin.mp.KoinPlatform.getKoin

class SignUpScreenModel(private val authRepository: AuthRepository) : ScreenModel {

    private val prefs = getKoin().get<AppCacheSetting>()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _googleProcessDialog = MutableStateFlow(false)
    val googleProcessDialog = _googleProcessDialog.asStateFlow()

    private val _password = MutableStateFlow(TextFieldValue(""))
    val password = _password.asStateFlow()

    private val _signUpState = MutableStateFlow<SignUpUiState>(UiState.Idle)
    val signUpState = _signUpState.asStateFlow()

    val userRoles = mutableStateOf(getUserRoles())

    val googleUser = mutableStateOf<GoogleUser?>(null)

    private val _selectedUserRole = MutableStateFlow<Int>(0)
    val selectedUserRole = _selectedUserRole.asStateFlow()

    fun onUserRoleSelected(value: Int) {
        _selectedUserRole.value = value
    }

    fun onNameChange(newValue: String) {
        _name.value = newValue
    }

    fun onEmailChange(newValue: String) {
        _email.value = newValue
    }

    fun onPasswordChange(newValue: TextFieldValue) {
        _password.value = newValue
    }

    fun changeGoogleDialogState(newValue: Boolean) {
        _googleProcessDialog.value = newValue
    }

    fun signUp() {
        screenModelScope.launch {
            _signUpState.value = UiState.Loading
            val validationResult = validateInputs()
            if (validationResult != null) {
                _signUpState.value = UiState.ValidationError(validationResult)
                return@launch
            }
            if (ConnectivityManager.isConnected) {
                val result = authRepository.signUp(
                    SignUpRequest(
                        username = _name.value,
                        email = _email.value,
                        password = _password.value.text,
                        userType = getUserRoles()[_selectedUserRole.value].name
                    )
                )
                result.onSuccess {
                    it.value?.let { res ->
                        updateRegisterPref(res)
                        _signUpState.value = UiState.Success(res.user?.isVerified == true)
                    }
                }.onError {
                    _signUpState.value = UiState.Error(it.detail)
                }
            } else {
                _signUpState.value = UiState.Error("Not Connected to Internet")
            }
        }
    }

    fun signUpWithGoogle() {
        screenModelScope.launch {
            _signUpState.value = UiState.Loading
            if (googleUser.value != null) {
                if (ConnectivityManager.isConnected) {
                    val result = authRepository.signUp(
                        SignUpRequest(
                            username = googleUser.value!!.displayName,
                            email = googleUser.value!!.email,
                            password = googleUser.value!!.email!!.reversed(),
                            userType = getUserRoles()[_selectedUserRole.value].name
                        )
                    )
                    result.onSuccess {
                        it.value?.let { res ->
                            updateRegisterPref(res)
                            _signUpState.value = UiState.Success(res.user?.isVerified == true)
                        }
                    }.onError {
                        _signUpState.value = UiState.Error(it.detail)
                    }
                } else {
                    _signUpState.value = UiState.Error("Not Connected to Internet")
                }
            } else {
                showErrorState("Google Sign up Failed")
            }
        }
    }

    fun showErrorState(message: String) {
        screenModelScope.launch {
            if (_signUpState.value != UiState.Loading) {
                _signUpState.value = UiState.Loading
                delay(1000L)
            }
            _signUpState.value = UiState.Error(message)
        }
    }

    private fun updateRegisterPref(loginResponse: SignUpResponse) {
        prefs.accessToken = loginResponse.accessToken.toString()
        prefs.userId = loginResponse.user?.userId.toString()
        prefs.isVerified = loginResponse.user?.isVerified == true
        prefs.userName = loginResponse.user?.username.toString()
        prefs.userEmail = loginResponse.user?.email.toString()
        prefs.userRole = loginResponse.user?.userType?.let { UserRole.getUserRole(it)?.name } ?: ""
    }

    private fun validateInputs(): List<String>? {
        val errors = mutableListOf<String>()

        if (_name.value.isBlank()) {
            errors.add("Please enter your name")
        }

        if (_email.value.isBlank()) {
            errors.add("Please Enter an email address")
        } else if (!isValidEmail(_email.value)) {
            errors.add("Invalid email format.")
        }

        if (_password.value.text.isBlank()) {
            errors.add("Please Enter a password")
        } else if (_password.value.text.length < 6) {
            errors.add("Password must be at least 6 characters long.")
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
}