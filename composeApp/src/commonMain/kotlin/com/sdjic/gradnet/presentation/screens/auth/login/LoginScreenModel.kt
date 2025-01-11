package com.sdjic.gradnet.presentation.screens.auth.login

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.sdjic.gradnet.data.network.utils.onError
import com.sdjic.gradnet.data.network.utils.onSuccess
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.repo.AuthRepository
import com.sdjic.gradnet.presentation.helper.ConnectivityManager
import com.sdjic.gradnet.presentation.helper.LoginUiState
import com.sdjic.gradnet.presentation.helper.UiState
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
                delay(200)
                _loginState.value = UiState.Idle
                return@launch
            }
            if(ConnectivityManager.isConnected){
                val result = authRepository.login(email.value.text, password.value.text)
                result.onSuccess {
                    prefs.accessToken = it.value?.accessToken.toString()
                    prefs.userId  =it.value?.userDto?.userId.toString()
                    _loginState.value = UiState.Success(it)
                }.onError {
                    _loginState.value = UiState.Error(it.detail)
                }
            }else{
                _loginState.value = UiState.Error("Not Connected to Internet")
            }
        }
    }

    private fun validateInputs(): Map<String, List<String>>? {
        val errors = mutableMapOf<String, MutableList<String>>()
        if (email.value.text.isBlank()) {
            errors.getOrPut("email") { mutableListOf() }.add("Email cannot be empty.")
        } else if (!isValidEmail(email.value.text)) {
            errors.getOrPut("email") { mutableListOf() }.add("Invalid email format.")
        }

        if (password.value.text.isBlank()) {
            errors.getOrPut("password") { mutableListOf() }.add("Password cannot be empty.")
        } else if (password.value.text.length < 6) {
            errors.getOrPut("password") { mutableListOf() }
                .add("Password must be at least 6 characters long.")
        }

        return if (errors.isEmpty()) null else errors
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email.matches(Regex(emailRegex))
    }
}