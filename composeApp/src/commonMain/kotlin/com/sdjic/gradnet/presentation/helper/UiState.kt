package com.sdjic.gradnet.presentation.helper

import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.compositionLocalOf
import cafe.adriel.voyager.navigator.Navigator
import com.sdjic.gradnet.presentation.core.model.UserProfile
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

sealed class UploadDialogState {
    data object Idle : UploadDialogState() // Dialog hidden
    data object Starting : UploadDialogState() // Shows "Starting upload..."
    data class Progress(val progress: Float) : UploadDialogState() // Shows progress bar
    data class Success(val message: String) : UploadDialogState() // Shows success message
    data class Error(val error: String) : UploadDialogState() // Shows error message
}


typealias LoginUiState = UiState<Boolean>
typealias SignUpUiState = UiState<Boolean>
typealias SetUpOrEditUiState = UiState<String>
typealias FetchUserUiState = UiState<UserProfile>

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