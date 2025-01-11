package com.sdjic.gradnet.data.network.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("createdAt")
    val createdAt: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("isVerified")
    val isVerified: Boolean?,
    @SerialName("type")
    val type: String?,
    @SerialName("updatedAt")
    val updatedAt: String?,
    @SerialName("userId")
    val userId: String?,
    @SerialName("username")
    val username: String?
)