package com.sdjic.gradnet.presentation.helper

import com.sdjic.gradnet.data.network.entity.LoginResponse
import com.sdjic.gradnet.data.network.entity.ServerResponse
import com.sdjic.gradnet.data.network.entity.SignUpResponse
import com.sdjic.gradnet.presentation.core.model.BaseUser

sealed class UiState<out T> {
    data object Idle : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String?) : UiState<Nothing>()
    data class ValidationError(val errors: Map<String, List<String>>) : UiState<Nothing>()
}

typealias LoginUiState = UiState<ServerResponse<LoginResponse>>
typealias SignUpUiState = UiState<ServerResponse<SignUpResponse>>
typealias SetUpOrEditUiState = UiState<BaseUser>