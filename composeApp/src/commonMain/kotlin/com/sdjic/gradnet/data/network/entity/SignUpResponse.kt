package com.sdjic.gradnet.data.network.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    @SerialName("authToken")
    val accessToken: String?,
    @SerialName("user")
    val user: UserDto?
)