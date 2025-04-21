package com.sdjic.gradnet.data.network.entity.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordResponse(
    @SerialName("message") val message: String,
    @SerialName("status") val status: Boolean
)