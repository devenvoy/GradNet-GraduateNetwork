package com.sdjic.gradnet.presentation.helper

import com.dokar.sonner.ToastType
import com.dokar.sonner.ToasterDefaults
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.time.Duration

object ToastManager {

    private val _messages = MutableSharedFlow<ToastMessage>()
    val messages = _messages.asSharedFlow()

    suspend fun showMessage(message: ToastMessage) {
        _messages.emit(message)
    }
}

data class ToastMessage(
    val message: String,
    val type: ToastType,
    val duration: Duration = ToasterDefaults.DurationDefault
)