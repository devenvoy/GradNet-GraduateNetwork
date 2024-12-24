package com.sdjic.gradnet.presentation.helper

sealed class UiState<out T> {
    data object Idle : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String?) : UiState<Nothing>()
    data class ValidationError(val errors: Map<String, List<String>>) : UiState<Nothing>()
}

typealias LoginUiState = UiState<Any>
typealias SignUpUiState = UiState<Any>