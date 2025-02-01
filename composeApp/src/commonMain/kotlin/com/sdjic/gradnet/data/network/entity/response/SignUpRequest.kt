package com.sdjic.gradnet.data.network.entity.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName("email")
    val email: String?,
    @SerialName("password")
    val password: String?,
    @SerialName("role")
    val userType: String?,
    @SerialName("name")
    val username: String?
)