package com.sdjic.gradnet.presentation.helper

import com.sdjic.gradnet.presentation.core.model.ToastMessage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object ToastManager {

    private val _messages = MutableSharedFlow<ToastMessage>()
    val messages = _messages.asSharedFlow()

    suspend fun showMessage(message: ToastMessage) {
        _messages.emit(message)
    }
}

