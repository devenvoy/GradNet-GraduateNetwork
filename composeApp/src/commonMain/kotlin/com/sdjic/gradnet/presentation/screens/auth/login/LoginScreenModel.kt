package com.sdjic.gradnet.presentation.screens.auth.login

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.mmk.kmpauth.google.GoogleUser
import com.sdjic.commons.helper.ConnectivityManager
import com.sdjic.commons.helper.UiState
import com.sdjic.commons.utils.onError
import com.sdjic.commons.utils.onSuccess
import com.sdjic.domain.AppCacheSetting
import com.sdjic.domain.repo.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin

class LoginScreenModel(private val authRepository: AuthRepository) : ScreenModel {

    val email = mutableStateOf(TextFieldValue(""))
    val password = mutableStateOf(TextFieldValue(""))
    private val _loginState = MutableStateFlow<LoginUiState>(UiState.Idle)
    val loginState: MutableStateFlow<LoginUiState> = _loginState

    private val prefs = getKoin().get<AppCacheSetting>()

    fun login() {
        screenModelScope.launch {
            _loginState.value = UiState.Loading
            val validationResult = validateInputs()
            if (validationResult != null) {
                _loginState.value = UiState.ValidationError(validationResult)
                return@launch
            }
            if (ConnectivityManager.isConnected) {
                val result = authRepository.login(email.value.text, password.value.text)
                result.onSuccess {
                    it.value?.let { res ->
                        prefs.accessToken = res.accessToken.toString()
                        prefs.userId = res.userDto?.userId.toString()
                        prefs.isVerified = res.userDto?.isVerified == true
                        _loginState.value = UiState.Success(res.userDto?.isVerified == true)
                    }
                }.onError {
                    _loginState.value = UiState.Error(it.detail)
                }
            } else {
                _loginState.value = UiState.Error("Not Connected to Internet")
            }
        }
    }

    fun loginWithGoogle(googleUser: GoogleUser) {
        screenModelScope.launch {
            _loginState.value = UiState.Loading
            // Send this idToken to your backend to verify
            val idToken = googleUser.idToken
            if (ConnectivityManager.isConnected) {
                val result = authRepository.login(googleUser.email!!, googleUser.email!!.reversed())
                result.onSuccess {
                    it.value?.let { res ->
                        prefs.accessToken = res.accessToken.toString()
                        prefs.userId = res.userDto?.userId.toString()
                        prefs.isVerified = res.userDto?.isVerified == true
                        _loginState.value = UiState.Success(res.userDto?.isVerified == true)
                    }
                }.onError {
                    _loginState.value = UiState.Error(it.detail)
                }
            } else {
                _loginState.value = UiState.Error("Not Connected to Internet")
            }
        }
    }

    fun showErrorState(message: String) {
        screenModelScope.launch {
            if (_loginState.value != UiState.Loading) {
                _loginState.value = UiState.Loading
                delay(1000L)
            }
            _loginState.value = UiState.Error(message)
        }
    }

    private fun validateInputs(): List<String>? {
        val errors = mutableListOf<String>()
        if (email.value.text.isBlank()) {
            errors.add("Email cannot be empty.")
        } else if (!isValidEmail(email.value.text)) {
            errors.add("Invalid email format.")
        }

        if (password.value.text.isBlank()) {
            errors.add("Password cannot be empty.")
        } else if (password.value.text.length < 6) {
            errors.add("Password must be at least 6 characters long.")
        }

        return if (errors.isEmpty()) null else errors
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email.matches(Regex(emailRegex))
    }
}