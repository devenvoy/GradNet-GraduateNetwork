package com.sdjic.domain.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("access_token") val accessToken: String?,
    @SerialName("user") val userDto: UserDto?
)