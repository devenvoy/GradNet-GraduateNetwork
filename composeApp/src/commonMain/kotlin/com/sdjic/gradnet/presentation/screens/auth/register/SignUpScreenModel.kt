package com.sdjic.gradnet.presentation.screens.auth.register

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.mmk.kmpauth.google.GoogleUser
import com.sdjic.gradnet.data.network.entity.ServerResponse
import com.sdjic.gradnet.data.network.entity.SignUpRequest
import com.sdjic.gradnet.data.network.entity.SignUpResponse
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

    private val _phone = MutableStateFlow("")
    val phone = _phone.asStateFlow()

    private val _googleProcessDialog = MutableStateFlow(false)
    val googleProcessDialog = _googleProcessDialog.asStateFlow()

    private val _password = MutableStateFlow(TextFieldValue(""))
    val password = _password.asStateFlow()

    private val _signUpState = MutableStateFlow<SignUpUiState>(UiState.Idle)
    val signUpState = _signUpState.asStateFlow()

    val userRoles = mutableStateOf(getUserRoles())

    val googleUser = mutableStateOf<GoogleUser?>(null)

    private val _selectedUserRole = MutableStateFlow<UserRole?>(null)
    val selectedUserRole = _selectedUserRole.asStateFlow()

    fun onUserRoleSelected(userRole: UserRole) {
        _selectedUserRole.value = userRole
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

    fun onPhoneChange(newValue: String) {
        _phone.value = newValue
    }

    fun changeGoogleDialogState(newValue: Boolean){
        _googleProcessDialog.value = newValue
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
            if (ConnectivityManager.isConnected) {
                val result = authRepository.signUp(
                    SignUpRequest(
                        username = _name.value,
                        email = _email.value,
                        password = _password.value.text,
                        userType = _selectedUserRole.value!!.name
                    )
                )
                result.onSuccess { it: ServerResponse<SignUpResponse> ->
                    prefs.accessToken = it.value?.accessToken.toString()
                    prefs.userId = it.value?.user?.userId.toString()
                    _signUpState.value = UiState.Success(it)
                }.onError {
                    _signUpState.value = UiState.Error(it.detail)
                }
            } else {
                _signUpState.value = UiState.Error("Not Connected to Internet")
            }
        }
    }

    fun signUpWithGoogle(){
        screenModelScope.launch {
            _signUpState.value = UiState.Loading
            if(googleUser.value != null){
                if(_selectedUserRole.value != null){
                    if (ConnectivityManager.isConnected) {
                        val result = authRepository.signUp(
                            SignUpRequest(
                                username = googleUser.value!!.displayName,
                                email =  googleUser.value!!.email,
                                password =  googleUser.value!!.email!!.reversed(),
                                userType = _selectedUserRole.value!!.name
                            )
                        )
                        result.onSuccess { it: ServerResponse<SignUpResponse> ->
                            prefs.accessToken = it.value?.accessToken.toString()
                            prefs.userId = it.value?.user?.userId.toString()
                            _signUpState.value = UiState.Success(it)
                        }.onError {
                            _signUpState.value = UiState.Error(it.detail)
                        }
                    } else {
                        _signUpState.value = UiState.Error("Not Connected to Internet")
                    }
                }else{
                    showErrorState("Please select your account type")
                }
            }else{
                showErrorState("Google Sign up Failed")
            }
        }
    }

    fun showErrorState(message: String){
        screenModelScope.launch {
            if(_signUpState.value != UiState.Loading){
                _signUpState.value = UiState.Loading
                delay(1000L)
            }
            _signUpState.value = UiState.Error(message)
        }
    }

    private fun validateInputs(): Map<String, List<String>>? {
        val errors = mutableMapOf<String, MutableList<String>>()

        if (_name.value.isBlank()) {
            errors.getOrPut("name") { mutableListOf() }.add("Please enter your name")
        }

        if (_email.value.isBlank()) {
            errors.getOrPut("email") { mutableListOf() }.add("Please Enter an email address")
        } else if (!isValidEmail(_email.value)) {
            errors.getOrPut("email") { mutableListOf() }.add("Invalid email format.")
        }

        if (_password.value.text.isBlank()) {
            errors.getOrPut("password") { mutableListOf() }.add("Please Enter a password")
        } else if (_password.value.text.length < 6) {
            errors.getOrPut("password") { mutableListOf() }
                .add("Password must be at least 6 characters long.")
        }

        if (_selectedUserRole.value != null) {
            errors.getOrPut("role") { mutableListOf() }.add("Please select a your account type.")
        }

       /* if (_phone.value.isBlank()) {
            errors.getOrPut("phone") { mutableListOf() }.add("Phone cannot be empty.")
        } else if (!isValidPhone(_phone.value)) {
            errors.getOrPut("phone") { mutableListOf() }.add("Invalid phone number.")
        }*/

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