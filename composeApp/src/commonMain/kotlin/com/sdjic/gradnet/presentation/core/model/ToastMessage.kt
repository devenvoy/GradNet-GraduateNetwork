package com.sdjic.gradnet.presentation.core.model

import com.dokar.sonner.ToastType
import com.dokar.sonner.ToasterDefaults
import kotlin.time.Duration

data class ToastMessage(
    val message: String,
    val type: ToastType,
    val duration: Duration = ToasterDefaults.DurationDefault
)