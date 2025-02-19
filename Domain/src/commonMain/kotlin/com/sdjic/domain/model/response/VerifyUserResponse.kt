package com.sdjic.domain.model.response
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyUserResponse(
    @SerialName("email") var email: String,
    @SerialName("verify_id") var verifyId: Int
)