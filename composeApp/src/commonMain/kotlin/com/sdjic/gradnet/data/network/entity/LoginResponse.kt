package com.sdjic.gradnet.data.network.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("access_token")
    val accessToken: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("user")
    val userDto: UserDto?
)