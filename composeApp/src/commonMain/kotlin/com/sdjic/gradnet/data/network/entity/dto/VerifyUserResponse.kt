package com.sdjic.gradnet.data.network.entity.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyUserResponse(
    @SerialName("email") var email: String,
    @SerialName("verify_id") var verifyId: Int
)