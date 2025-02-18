package com.sdjic.gradnet.data.network.entity.response

import com.sdjic.gradnet.data.network.entity.dto.UserDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("access_token")
    val accessToken: String?,
    @SerialName("user")
    val userDto: UserDto?
)