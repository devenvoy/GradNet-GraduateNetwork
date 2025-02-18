package com.sdjic.commons.helper

import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.compositionLocalOf
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

sealed class UiState<out T> {
    data object Idle : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()

    data class Error(
        val message: String?,
        val timestamp: Instant = Clock.System.now()
    ) : UiState<Nothing>()

    data class ValidationError(
        val errors: List<String>,
        val timestamp: Instant = Clock.System.now()
    ) : UiState<Nothing>()
}

@OptIn(ExperimentalMaterial3Api::class)
val LocalScrollBehavior = compositionLocalOf<BottomAppBarScrollBehavior> {
    error("No ScrollBehavior provided")
}

val LocalRootNavigator = compositionLocalOf<Navigator> {
    error("No Navigator provided")
}

val LocalDrawerController = compositionLocalOf<DrawerState>{
    error("No DrawerState provided")
}